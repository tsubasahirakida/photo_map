package com.example.demo.repository.like;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Like;

@Repository
public class LikeDaoImpl implements LikeDao {
    
    private final JdbcTemplate jdbcTemplate;

	public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
	public List<Integer> findMyLikes(int id) {
		String sql = "SELECT post_id FROM likes WHERE user_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		List<Integer> list = new ArrayList<Integer>();
		for (Map<String, Object> result : resultList) {
			list.add((Integer) result.get("post_id"));
		}
		return list;
	}

	@Override
	public void createLike(Like like) {
		jdbcTemplate.update("INSERT INTO likes(user_id, post_id) VALUES(?, ?)",
				like.getUser_id(), like.getPost_id());
	}

	@Override
	public void deleteLike(Like like) {
		jdbcTemplate.update("DELETE FROM likes WHERE user_id = ? AND post_id = ?",
				like.getUser_id(), like.getPost_id());
	}
	
}