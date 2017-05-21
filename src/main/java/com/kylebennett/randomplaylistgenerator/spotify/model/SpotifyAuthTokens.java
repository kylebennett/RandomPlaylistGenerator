package com.kylebennett.randomplaylistgenerator.spotify.model;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by kyle on 21/05/17.
 */
public class SpotifyAuthTokens {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    @JsonSetter("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonSetter("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    @JsonSetter("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    @JsonSetter("expires_in")
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("accessToken", accessToken)
                .append("refreshToken", refreshToken)
                .append("tokenType", tokenType)
                .append("expiresIn", expiresIn)
                .toString();
    }
}
