package com.kylebennett.randomplaylistgenerator.spotify.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class AuthControllerTest {

	@MockBean
	private AuthHandler authHandler;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getAuthToken_ReceivesAuthCode_HandlerGetsTokens() {

		final String authorisationCode = "authorisationCode";

		final ResponseEntity<String> response = restTemplate.getForEntity(
				"/authorisationCode?code=" + authorisationCode, String.class);

		verify(authHandler).getTokens(authorisationCode);

		assertThat(response.getStatusCode()).isIn(HttpStatus.FOUND);
	}
}
