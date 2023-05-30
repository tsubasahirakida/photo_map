package com.example.demo.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.repository.post.PostDao;

@Service
public class PostServiceImpl implements PostService {

	private final PostDao dao;
	
	PostServiceImpl(PostDao dao){
		this.dao = dao;
	}

	@Override
	public void save(Post post) {
		dao.createPost(post);
	}

	@Override
	public List<Post> getAll() {
		var list = dao.getAll();

//		if (list.isEmpty()) {
//			throw new InquiryNotFoundException("SQL error");
//		}

		return list;
	}

	@Override
	public Post show(int id) {
		Post result = dao.show(id);
		return result;
	}

	@Override
	public void update(Post post) {
		dao.updatePost(post);		
	}

	@Override
	public void delete(Long id) {
		dao.deletePost(id);
	}

}
