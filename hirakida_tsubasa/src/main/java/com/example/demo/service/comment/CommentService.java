package com.example.demo.service.comment;

import java.util.List;

import com.example.demo.entity.Comment;

public interface CommentService {
    void save(Comment comment);
    List<Comment> getAll(int postId);
    void deleteComment(int commentId);
}
