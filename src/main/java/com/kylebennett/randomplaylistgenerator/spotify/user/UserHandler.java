package com.kylebennett.randomplaylistgenerator.spotify.user;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kylebennett.randomplaylistgenerator.spotify.auth.AuthHandler;
import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyUserProfile;

@Component
public class UserHandler {

	private static final Logger LOG = LogManager.getLogger(UserHandler.class);

	@Value("${spotify.user.profile.url}")
	private String userProfileUrl;

	@Autowired
	private AuthHandler authHandler;

	private SpotifyUserProfile currentUser = null;

	private void getUserProfile() {

		if (authHandler.getAccessToken() != null) {
			LOG.debug("Getting User Profile from Spotify");

			final CloseableHttpClient httpclient = HttpClients.createDefault();

			final HttpGet userProfileRequest = new HttpGet(userProfileUrl);

			userProfileRequest.addHeader("Authorization", "Bearer "
					+ authHandler.getAccessToken());

			try {
				final UserProfileResponseHandler rh = new UserProfileResponseHandler();
				final SpotifyUserProfile authResponse = httpclient.execute(
						userProfileRequest, rh);

				LOG.debug("Spotify Auth Response [{}]", authResponse);

				this.currentUser = authResponse;

			} catch (final IOException ioe) {
				LOG.error("IOException handling Response", ioe);
			}
		} else {
			LOG.error("Can't get User Profile, No Access Token available");
		}
	}

	public SpotifyUserProfile getCurrentUser() {

		if (currentUser == null) {
			getUserProfile();
		}

		return currentUser;
	}
}
