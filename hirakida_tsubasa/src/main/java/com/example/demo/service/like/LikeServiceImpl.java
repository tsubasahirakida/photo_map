package com.example.demo.service.like;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.repository.like.LikeDao;

@Service
public class LikeServiceImpl implements LikeService {

	private final LikeDao dao;
	
	LikeServiceImpl(LikeDao dao){
		this.dao = dao;
	}

	@Override
	public List<Integer> findMyLikes(int id) {
		var list = dao.findMyLikes(id);
		return list;
	}

	@Override
	public void createLike(Like like) {
		dao.createLike(like);
	}

	@Override
	public void deleteLike(Like like) {
		dao.deleteLike(like);
	}
}
