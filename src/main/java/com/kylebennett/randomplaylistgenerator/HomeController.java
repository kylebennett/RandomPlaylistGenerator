package com.kylebennett.randomplaylistgenerator;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kylebennett.randomplaylistgenerator.spotify.auth.AuthHandler;
import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyUserProfile;
import com.kylebennett.randomplaylistgenerator.spotify.user.UserHandler;

/**
 * Created by kyle on 19/05/17.
 */
@Controller
public class HomeController {

	private static final Logger LOG = LogManager
			.getLogger(HomeController.class);

	@Autowired
	private AuthHandler authHandler;

	@Autowired
	private UserHandler userHandler;

	@RequestMapping("/")
	public String displayHomePage(final Map<String, Object> model) {

		final String authUrl = authHandler.buildSpotifyAuthUrl();
		model.putIfAbsent("authUrl", authUrl);

		model.putIfAbsent("authToken", authHandler.getAccessToken());

		final SpotifyUserProfile currentUser = userHandler.getCurrentUser();

		if (currentUser != null) {
			model.putIfAbsent("currentUser", currentUser.getId());
		}
		return "home";
	}
}
