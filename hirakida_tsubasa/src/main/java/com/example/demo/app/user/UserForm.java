package com.example.demo.app.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserForm {
    
    @Size(max=20)
    @NotNull
    private String name;

    @Size(max=64)
    @NotNull
    private String email;

    @Size(max=64)
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
