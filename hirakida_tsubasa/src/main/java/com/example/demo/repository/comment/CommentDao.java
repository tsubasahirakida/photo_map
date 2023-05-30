package com.example.demo.repository.comment;

import java.util.List;

import com.example.demo.entity.Comment;

public interface CommentDao {
    void createComment(Comment comment);
    List<Comment> getAll(int postId);
    void deleteComment(int commentId);
}
