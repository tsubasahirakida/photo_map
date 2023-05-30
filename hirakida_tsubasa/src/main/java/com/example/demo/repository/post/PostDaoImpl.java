package com.example.demo.repository.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Post;

@Repository
public class PostDaoImpl implements PostDao {

	private final JdbcTemplate jdbcTemplate;

	public PostDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Post> getAll() {
		String sql = "SELECT posts.id, posts.body, posts.place, posts.image_url, posts.user_id, users.name FROM posts INNER JOIN users ON posts.user_id = users.id";
		String shortening_image_url = "";
		String relative_path = "";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Post> list = new ArrayList<Post>();
		for (Map<String, Object> result : resultList) {
			Post post = new Post();
			post.setId((Integer) result.get("id"));
			post.setBody((String) result.get("body"));
			post.setPlace((String) result.get("place"));

			shortening_image_url = (String) result.get("image_url");
			relative_path = "images" + shortening_image_url.split("images")[1];
			post.setImage_url(relative_path);

			post.setUser_id((Integer) result.get("user_id"));
			post.setUserName((String) result.get("name"));

			list.add(post);
		}
		return list;
	}

	@Override
	public void createPost(Post post) {
        jdbcTemplate.update("INSERT INTO posts(body, place, image_url, user_id) VALUES(?, ?, ?, ?)",
				post.getBody(), post.getPlace(), post.getImage_url(), post.getUser_id());
	}

	@Override
	public Post show(int id) {
		// BeanPropertyRowMapper<>は、コンストラクタを使用する。
		// テーブルを結合する場合AS句で、名称の変更が必要
		String sql = "SELECT posts.id, posts.body, posts.place, posts.image_url, posts.user_id, users.name AS userName FROM posts INNER JOIN users ON posts.user_id = users.id WHERE posts.id = ?";
        RowMapper<Post> rowMapper = new BeanPropertyRowMapper<Post>(Post.class);
        Post post = jdbcTemplate.queryForObject(sql, rowMapper, id);
 
        return post;
	}

	@Override
	public void updatePost(Post post) {
		String sql = "UPDATE posts SET body=?, place=?, image_url=? WHERE id=?";
        jdbcTemplate.update(sql, post.getBody(), post.getPlace(), post.getImage_url(), post.getId());
	}

	@Override
	public void deletePost(Long id) {
		String sql = "DELETE FROM posts WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
