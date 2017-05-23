package com.kylebennett.randomplaylistgenerator.spotify.auth;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kyle on 19/05/17.
 */
@Controller
public class AuthController {

	private static final Logger LOG = LogManager
			.getLogger(AuthController.class);

	@Autowired
	private AuthHandler authHandler;

	@RequestMapping("/authorisationCode")
	public String getAuthToken(
			@RequestParam(name = "code") final String authToken,
			final Map<String, Object> model) {

		LOG.debug("Auth Token from Spotify [{}]", authToken);

		authHandler.setAuthorisationCode(authToken);

		authHandler.getTokens();

		return "redirect:/";
	}
}
