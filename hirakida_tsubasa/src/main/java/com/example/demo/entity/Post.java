package com.example.demo.entity;

public class Post {
	private int id;
	private String body;
	private String place;
	private String image_url;
	private int user_id;
	private String userName;
	
	public Post() {
	}

	public Post(int id, String body, String place, String image_url, int user_id, String userName) {
		this.id = id;
		this.body = body;
		this.place = place;
		this.image_url = image_url;
		this.user_id = user_id;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
