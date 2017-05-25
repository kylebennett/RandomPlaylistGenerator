package com.kylebennett.randomplaylistgenerator.spotify.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSetter;

public class SpotifyUserProfile {

	private String username;
	private String birthdate;
	private String country;
	private String email;
	private Map<String, String> externalUrls;
	private Followers followers;
	private String href;
	private String id;
	private Images images;
	private String product;
	private String type;
	private String uri;

	public String getUsername() {
		return username;
	}

	@JsonSetter("display_name")
	public void setUsername(final String username) {
		this.username = username;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(final String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Map<String, String> getExternalUrls() {
		return externalUrls;
	}

	@JsonSetter("external_urls")
	public void setExternalUrls(final Map<String, String> externalUrls) {
		this.externalUrls = externalUrls;
	}

	public Followers getFollowers() {
		return followers;
	}

	public void setFollowers(final Followers followers) {
		this.followers = followers;
	}

	public String getHref() {
		return href;
	}

	public void setHref(final String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(final Images images) {
		this.images = images;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(final String product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SpotifyUserProfile [username=");
		builder.append(username);
		builder.append(", birthdate=");
		builder.append(birthdate);
		builder.append(", country=");
		builder.append(country);
		builder.append(", email=");
		builder.append(email);
		builder.append(", externalUrls=");
		builder.append(externalUrls);
		builder.append(", followers=");
		builder.append(followers);
		builder.append(", href=");
		builder.append(href);
		builder.append(", id=");
		builder.append(id);
		builder.append(", images=");
		builder.append(images);
		builder.append(", product=");
		builder.append(product);
		builder.append(", type=");
		builder.append(type);
		builder.append(", uri=");
		builder.append(uri);
		builder.append("]");
		return builder.toString();
	}

	public class Followers {

		private String href;
		private int total;

		public String getHref() {
			return href;
		}

		public void setHref(final String href) {
			this.href = href;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(final int total) {
			this.total = total;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("Followers [href=");
			builder.append(href);
			builder.append(", total=");
			builder.append(total);
			builder.append("]");
			return builder.toString();
		}
	}

	public class Images {

		private int height;
		private int width;
		private String url;
		public int getHeight() {
			return height;
		}
		public void setHeight(final int height) {
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(final int width) {
			this.width = width;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(final String url) {
			this.url = url;
		}
		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("Images [height=");
			builder.append(height);
			builder.append(", width=");
			builder.append(width);
			builder.append(", url=");
			builder.append(url);
			builder.append("]");
			return builder.toString();
		}
	}

}
