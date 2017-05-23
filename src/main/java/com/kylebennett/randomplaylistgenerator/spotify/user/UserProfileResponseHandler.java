package com.kylebennett.randomplaylistgenerator.spotify.user;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyUserProfile;

public class UserProfileResponseHandler
		implements
			ResponseHandler<SpotifyUserProfile> {

	private static final Logger LOG = LogManager
			.getLogger(UserProfileResponseHandler.class);

	@Override
	public SpotifyUserProfile handleResponse(final HttpResponse response)
			throws ClientProtocolException, IOException {

		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();

		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		if (entity == null) {
			throw new ClientProtocolException("Response contains no content");
		}

		final Reader reader = new InputStreamReader(entity.getContent(),
				"utf-8");

		final ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(reader, SpotifyUserProfile.class);
	}
}