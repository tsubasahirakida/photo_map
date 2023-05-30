package com.example.demo.app.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentForm2 {

    @NotNull
    @Size(min = 1, max = 200, message = "Please input 200 characters or less")
    private String body;

    public CommentForm2(String body) {
        this.body = body;
    }

    public CommentForm2() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
