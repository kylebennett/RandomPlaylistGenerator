package com.kylebennett.randomplaylistgenerator.spotify.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyAuthTokens;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthHandlerTest {

	@Mock
	CloseableHttpClient httpClient;

	@Mock
	AuthResponseHandler responseHandler;

	@InjectMocks
	private AuthHandler handler;

	final String mockAuthUrl = "https://spotify.auth";
	final String mockClientId = "ClientId";
	final String mockSecret = "Secret";
	final String mockRedirectUri = "http://redirect";

	@Before
	public void setUp() throws Exception {

		handler = new AuthHandler();

		ReflectionTestUtils.setField(handler, "spotifyAuthUrl", mockAuthUrl);
		ReflectionTestUtils.setField(handler, "spotifyClientId", mockClientId);
		ReflectionTestUtils.setField(handler, "spotifyRedirectUri",
				mockRedirectUri);
		ReflectionTestUtils
		.setField(handler, "spotifyClientSecret", mockSecret);

	}

	@Test
	public void buildSpotifyAuthUrl_ReturnsAuthUrl() throws Exception {

		final String expectedUrl = mockAuthUrl + "?client_id=" + mockClientId
				+ "&response_type=code&redirect_uri="
				+ URLEncoder.encode(mockRedirectUri, "utf-8");

		final String authUrl = handler.buildSpotifyAuthUrl();
		assertThat(authUrl).isEqualTo(expectedUrl);
	}

	@Test
	public void buildAuthHeader_ReturnsAuthHeader() {

		final String idAndSecret = mockClientId + ":" + mockSecret;
		final String expectedAuthHeader = "Basic "
				+ Base64.getEncoder().encodeToString(idAndSecret.getBytes());

		final String authHeader = handler.buildAuthHeader();

		assertThat(authHeader).isEqualTo(expectedAuthHeader);
	}

	@Test
	public void getTokens_WhenValidResponse_StoresAuthTokens()
			throws ClientProtocolException, IOException {

		final CloseableHttpResponse mockHttpResponse = Mockito
				.mock(CloseableHttpResponse.class);

		final SpotifyAuthTokens tokens = new SpotifyAuthTokens();

		when(httpClient.execute(any())).thenReturn(mockHttpResponse);
		when(responseHandler.handleResponse(mockHttpResponse)).thenReturn(
				tokens);
	}
}
