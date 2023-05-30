package com.example.demo.entity;

public class Comment {
    private int id;
	private String body;
    private int post_id;
	private int user_id;
    private String userName;
    
    public Comment() {
    }

    public Comment(int id, String body, int post_id, int user_id, String userName) {
        this.id = id;
        this.body = body;
        this.post_id = post_id;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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
