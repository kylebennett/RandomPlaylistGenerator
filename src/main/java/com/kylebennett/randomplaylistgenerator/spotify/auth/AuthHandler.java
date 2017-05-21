package com.kylebennett.randomplaylistgenerator.spotify.auth;

import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyAuthTokens;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by kyle on 20/05/17.
 */
@Component
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

    public String getAccessToken() {

        return authTokens != null ? authTokens.getAccessToken() : null;
    }

    public String buildSpotifyAuthUrl() {

        String authUrl = null;

        try {
            authUrl = spotifyAuthUrl +
                    "?client_id=" + spotifyClientId +
                    "&response_type=code" +
                    "&redirect_uri=" + URLEncoder.encode(spotifyRedirectUri, "utf-8");

            //TODO - Generate and store random state value

        } catch (UnsupportedEncodingException uee) {
            LOG.error("Cannot encode URL", uee);
        }
        LOG.debug("Spotify Auth URL [" + authUrl + "]");

        return authUrl;
    }
    
    public void setAuthorisationCode(String authorisationCode) {
        this.authorisationCode = authorisationCode;
    }

    public void getTokens() {

        LOG.debug("Getting Access Tokens");

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost tokenRequest = new HttpPost(tokenUrl);

        tokenRequest.addHeader("Authorization", buildAuthHeader());

        List<NameValuePair> nvps = new ArrayList<>();

        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("code", authorisationCode));
        nvps.add(new BasicNameValuePair("redirect_uri", spotifyRedirectUri));

        try {
            tokenRequest.setEntity(new UrlEncodedFormEntity(nvps));

        } catch (UnsupportedEncodingException uee) {

        }

        try {
            AuthResponseHandler rh = new AuthResponseHandler();
            SpotifyAuthTokens authResponse = httpclient.execute(tokenRequest, rh);

            LOG.debug("Spotify Auth Response [" + authResponse + "]");

            this.authTokens = authResponse;

        } catch (IOException ioe) {
            LOG.error("IOException handling Response", ioe);
        }
    }

    public void refreshTokens() {

        //TODO - refreshTokens

    }

    private String buildAuthHeader() {

        String encodedSecret = null;

        try {
            String secret = spotifyClientId + ":" + spotifyClientSecret;
            encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes("utf-8"));
        } catch (UnsupportedEncodingException uee) {
            LOG.error("Couldn't Encode Secrets", uee);
        }

        return "Basic " + encodedSecret;
    }
}
