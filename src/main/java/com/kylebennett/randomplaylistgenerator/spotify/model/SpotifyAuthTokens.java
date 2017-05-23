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
	public void setAccessToken(final String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	@JsonSetter("refresh_token")
	public void setRefreshToken(final String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	@JsonSetter("token_type")
	public void setTokenType(final String tokenType) {
		this.tokenType = tokenType;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	@JsonSetter("expires_in")
	public void setExpiresIn(final int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SpotifyAuthTokens [accessToken=");
		builder.append(accessToken);
		builder.append(", refreshToken=");
		builder.append(refreshToken);
		builder.append(", tokenType=");
		builder.append(tokenType);
		builder.append(", expiresIn=");
		builder.append(expiresIn);
		builder.append("]");
		return builder.toString();
	}

}
