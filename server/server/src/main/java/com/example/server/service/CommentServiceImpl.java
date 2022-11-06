package com.example.server.service;

import com.example.server.model.Comment;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> readAll(Post post) {
        List<Comment> comments = commentRepository.findAllCommentByPost(post.getId());
        for(Comment comment: comments){
            comment.setUser(new User(comment.getUser().getName()));
            comment.getPost().setUser(new User(comment.getPost().getUser().getName()));
        }
        return comments;
    }

    @Override
    public Integer getCountComment(Post post) {
        return commentRepository.findAllCommentByPost(post.getId()).size();
    }

    @Override
    public Comment read(Integer id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public boolean delete(Integer id) {
        Comment comment = commentRepository.findCommentById(id);
        if (comment!=null) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}
