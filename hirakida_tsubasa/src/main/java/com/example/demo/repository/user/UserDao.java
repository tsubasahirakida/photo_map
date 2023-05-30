package com.example.demo.repository.user;

import com.example.demo.entity.User;

public interface UserDao {
	void createUser(User user);
	User show(int id);
}
