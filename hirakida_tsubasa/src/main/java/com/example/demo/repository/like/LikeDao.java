package com.example.demo.repository.like;

import java.util.List;

import com.example.demo.entity.Like;

public interface LikeDao {
    List<Integer> findMyLikes(int id);
    void createLike(Like like);
    void deleteLike(Like like);
}
