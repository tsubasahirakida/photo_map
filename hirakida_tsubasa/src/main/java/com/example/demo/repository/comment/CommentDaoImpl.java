package com.example.demo.repository.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {

    private final JdbcTemplate jdbcTemplate;

	public CommentDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
    public void createComment(Comment comment) {
        jdbcTemplate.update("INSERT INTO comments(body, post_id, user_id) VALUES(?, ?, ?)",
				comment.getBody(), comment.getPost_id(), comment.getUser_id());
    }

    @Override
    public List<Comment> getAll(int postId) {
        String sql = "SELECT comments.id, comments.body, comments.user_id, users.name FROM comments INNER JOIN users ON comments.user_id = users.id WHERE comments.post_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, postId);
		List<Comment> list = new ArrayList<Comment>();
		for (Map<String, Object> result : resultList) {
			Comment comment = new Comment();
            comment.setId((Integer) result.get("id"));
            comment.setBody((String) result.get("body"));
            comment.setUser_id((Integer) result.get("user_id"));
            comment.setUserName((String) result.get("name"));

            list.add(comment);
		}
		return list;
    }

    @Override
    public void deleteComment(int commentId) {
        jdbcTemplate.update("DELETE FROM comments WHERE id = ?", commentId);
    }
    
}
