package com.example.demo.service.like;

import java.util.List;

import com.example.demo.entity.Like;

public interface LikeService {
    List<Integer> findMyLikes(int id);
    void createLike(Like like);
    void deleteLike(Like like);
}
