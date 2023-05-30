package com.example.demo.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.repository.comment.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao dao;
    
    public CommentServiceImpl(CommentDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Comment comment) {
        dao.createComment(comment);
    }

    @Override
    public List<Comment> getAll(int postId) {
        var list = dao.getAll(postId);
		return list;
    }

    @Override
    public void deleteComment(int commentId) {
        dao.deleteComment(commentId);
    }
    
}
