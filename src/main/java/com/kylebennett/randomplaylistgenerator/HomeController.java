package com.kylebennett.randomplaylistgenerator;

import com.kylebennett.randomplaylistgenerator.spotify.auth.AuthHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by kyle on 19/05/17.
 */
@Controller
public class HomeController {

    private static final Logger LOG = LogManager.getLogger(HomeController.class);

    @Autowired
    private AuthHandler authHandler;

    @RequestMapping("/")
    public String homePage(Map<String, Object> model) {

        LOG.debug("HomePage!!");

        String authUrl = authHandler.buildSpotifyAuthUrl();
        model.putIfAbsent("authUrl", authUrl);

        model.putIfAbsent("authToken", authHandler.getAccessToken());

        return "home";
    }


}
