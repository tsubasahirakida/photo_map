package com.example.demo.repository.post;

import java.util.List;

import com.example.demo.entity.Post;

public interface PostDao {
	void createPost(Post post);
	List<Post> getAll();
	Post show(int id);
	void updatePost(Post post);
	void deletePost(Long id);
}
