package com.example.demo.service.user;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.user.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao dao;
	
	UserServiceImpl(UserDao dao){
		this.dao = dao;
	}

	@Override
	public void save(User user) {
		dao.createUser(user);
	}

	@Override
	public User show(int id) {
		User result = dao.show(id);
		return result;
	}
}
