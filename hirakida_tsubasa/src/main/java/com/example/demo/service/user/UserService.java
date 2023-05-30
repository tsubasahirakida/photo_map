package com.example.demo.service.user;

import com.example.demo.entity.User;

public interface UserService {
	void save(User user);
	User show(int id);
}
