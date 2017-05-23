package com.kylebennett.randomplaylistgenerator.spotify.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyAuthTokens;

/**
 * Created by kyle on 20/05/17.
 */
@Component
@PropertySource("classpath:keys.properties")
public class AuthHandler {

	private static final Logger LOG = LogManager.getLogger(AuthHandler.class);

	@Value("${spotify.auth.code.url}")
	private String spotifyAuthUrl;

	@Value("${spotify.auth.token.url}")
	private String tokenUrl;

	@Value("${spotify.auth.clientID}")
	private String spotifyClientId;

	@Value("${spotify.auth.clientSecret}")
	private String spotifyClientSecret;

	@Value("${spotify.auth.redirectUri}")
	private String spotifyRedirectUri;

	private String authorisationCode = null;

	private SpotifyAuthTokens authTokens = null;

	private LocalDateTime lastRefreshTime = null;

	public String getAccessToken() {

		if (authTokens != null) {

			final LocalDateTime now = LocalDateTime.now();
			final LocalDateTime expiryTime = lastRefreshTime
					.plusSeconds(authTokens.getExpiresIn());

			LOG.debug("Tokens expire at [{}] Current time is [{}]", expiryTime,
					now);

			final boolean tokensExpired = now.isAfter(expiryTime);

			if (tokensExpired) {
				LOG.debug("Tokens have expired");
				refreshTokens();
			}

			return authTokens.getAccessToken();
		}

		return null;
	}
	public String buildSpotifyAuthUrl() {

		String authUrl = null;

		try {
			authUrl = spotifyAuthUrl + "?client_id=" + spotifyClientId
					+ "&response_type=code" + "&redirect_uri="
					+ URLEncoder.encode(spotifyRedirectUri, "utf-8");

			// TODO - Generate and store random state value

		} catch (final UnsupportedEncodingException uee) {
			LOG.error("Cannot encode URL", uee);
		}
		LOG.debug("Spotify Auth URL [{}]", authUrl);

		return authUrl;
	}

	public void setAuthorisationCode(final String authorisationCode) {
		this.authorisationCode = authorisationCode;
	}

	public void getTokens() {

		LOG.debug("Getting Access Tokens from Spotify");

		final CloseableHttpClient httpclient = HttpClients.createDefault();

		final HttpPost tokenRequest = new HttpPost(tokenUrl);

		tokenRequest.addHeader("Authorization", buildAuthHeader());

		final List<NameValuePair> nvps = new ArrayList<>();

		nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
		nvps.add(new BasicNameValuePair("code", authorisationCode));
		nvps.add(new BasicNameValuePair("redirect_uri", spotifyRedirectUri));

		// TODO - Scopes?

		try {
			tokenRequest.setEntity(new UrlEncodedFormEntity(nvps));

		} catch (final UnsupportedEncodingException uee) {

		}

		try {
			final AuthResponseHandler rh = new AuthResponseHandler();
			final SpotifyAuthTokens authResponse = httpclient.execute(
					tokenRequest, rh);

			LOG.debug("Spotify Auth Response [{}]", authResponse);

			this.authTokens = authResponse;
			this.lastRefreshTime = LocalDateTime.now();

		} catch (final IOException ioe) {
			LOG.error("IOException handling Response", ioe);
		}
	}

	public void refreshTokens() {

		LOG.debug("Refreshing Access Tokens");

		if (authTokens != null) {

			final CloseableHttpClient httpclient = HttpClients.createDefault();

			final HttpPost tokenRequest = new HttpPost(tokenUrl);

			tokenRequest.addHeader("Authorization", buildAuthHeader());

			final List<NameValuePair> nvps = new ArrayList<>();

			nvps.add(new BasicNameValuePair("grant_type", "refresh_token"));
			nvps.add(new BasicNameValuePair("refresh_token", authTokens
					.getRefreshToken()));

			try {
				tokenRequest.setEntity(new UrlEncodedFormEntity(nvps));

			} catch (final UnsupportedEncodingException uee) {

			}

			try {
				final AuthResponseHandler rh = new AuthResponseHandler();
				final SpotifyAuthTokens authResponse = httpclient.execute(
						tokenRequest, rh);

				LOG.debug("Spotify Refresh Response [{}]", authResponse);

				this.authTokens = authResponse;

			} catch (final IOException ioe) {
				LOG.error("IOException handling Response", ioe);
			}

		} else {
			LOG.error("Can't refresh Access Token, no Refresh Token found");
		}
	}
	private String buildAuthHeader() {

		String encodedSecret = null;

		try {
			final String secret = spotifyClientId + ":" + spotifyClientSecret;
			encodedSecret = Base64.getEncoder().encodeToString(
					secret.getBytes("utf-8"));
		} catch (final UnsupportedEncodingException uee) {
			LOG.error("Couldn't Encode Secrets", uee);
		}

		return "Basic " + encodedSecret;
	}
}
