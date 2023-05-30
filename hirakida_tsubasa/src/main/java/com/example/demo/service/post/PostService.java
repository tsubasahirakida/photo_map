package com.example.demo.service.post;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostService {
	void save(Post post);
	List<Post> getAll();
	Post show(int postId);
	void update(Post post);
	void delete(Long id);
}
