package com.kylebennett.randomplaylistgenerator.spotify.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kylebennett.randomplaylistgenerator.spotify.model.SpotifyAuthTokens;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by kyle on 21/05/17.
 */
public class AuthResponseHandler implements ResponseHandler<SpotifyAuthTokens> {

    private static final Logger LOG = LogManager.getLogger(AuthResponseHandler.class);

    @Override
    public SpotifyAuthTokens handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        if (entity == null) {
            throw new ClientProtocolException("Response contains no content");
        }

        Reader reader = new InputStreamReader(entity.getContent(), "utf-8");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(reader, SpotifyAuthTokens.class);
    }
}
