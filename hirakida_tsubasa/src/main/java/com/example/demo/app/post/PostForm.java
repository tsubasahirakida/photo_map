package com.example.demo.app.post;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostForm {
	
	@NotNull
	@Size(min = 1, max = 200, message="Please input 200 characters or less")
    private String body;
	
	@NotNull
	@Size(min = 1, max = 200, message="Please input 200 characters or less")
    private String place;
	
	@NotNull
    private MultipartFile image_url;
    
	public PostForm(String body, String place, MultipartFile image_url) {
		super();
		this.body = body;
		this.place = place;
		this.image_url = image_url;
	}

	public PostForm() {
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

	public MultipartFile getImage_url() {
		return image_url;
	}

	public void setImage_url(MultipartFile image_url) {
		this.image_url = image_url;
	}

	public void setId(int id) {
	}
    
}
