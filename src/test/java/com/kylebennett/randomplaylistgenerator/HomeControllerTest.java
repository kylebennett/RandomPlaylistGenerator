/**
 *
 */
package com.kylebennett.randomplaylistgenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kylebennett.randomplaylistgenerator.spotify.auth.AuthHandler;
import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyUserProfile;
import com.kylebennett.randomplaylistgenerator.spotify.user.UserHandler;
/**
 * @author Kyle
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HomeControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private AuthHandler mockAuthHandler;

	@MockBean
	private UserHandler mockUserHandler;

	@Mock
	private SpotifyUserProfile mockProfile;

	@Test
	public void displayHomePage_NotLoggedIn_ReturnsHomeTemplate()
			throws Exception {

		final String mockAuthUrl = "MockAuthUrl";

		given(mockAuthHandler.buildSpotifyAuthUrl()).willReturn(mockAuthUrl);
		given(mockUserHandler.getCurrentUser()).willReturn(null);

		final ResponseEntity<String> entity = this.restTemplate.getForEntity(
				"/", String.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains(mockAuthUrl);
		assertThat(entity.getBody()).contains("Not Logged In");
	}

	@Test
	public void displayHomePage_LoggedIn_ReturnsHomeTemplateWithUserProfile()
			throws Exception {

		final String mockAuthUrl = "MockAuthUrl";
		final String mockUsername = "Username";

		given(mockAuthHandler.buildSpotifyAuthUrl()).willReturn(mockAuthUrl);
		given(mockUserHandler.getCurrentUser()).willReturn(mockProfile);

		when(mockProfile.getUsername()).thenReturn(mockUsername);

		final ResponseEntity<String> entity = this.restTemplate.getForEntity(
				"/", String.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).doesNotContain(mockAuthUrl);
		assertThat(entity.getBody()).contains(mockUsername);
	}
}
