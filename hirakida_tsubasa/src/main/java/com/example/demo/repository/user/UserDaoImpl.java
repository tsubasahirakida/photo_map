package com.example.demo.repository.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	private final JdbcTemplate jdbcTemplate;

	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createUser(User user) {
        jdbcTemplate.update("INSERT INTO users(name, email, password) VALUES(?, ?, ?)",
				user.getName(), user.getEmail(), user.getPassword());
	}

	@Override
	public User show(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
 
        return user;
	}
}
