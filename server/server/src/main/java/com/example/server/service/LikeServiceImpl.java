package com.example.server.service;

import com.example.server.model.Like;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void create(Like like) {
        if(likeRepository.findLikeByUserAndPost(like.getUser(), like.getPost()) == null){
            likeRepository.save(like);
        }
    }

    @Override
    public List<Like> readAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like read(User fromUser, Post post) {
        return likeRepository.findLikeByUserAndPost(fromUser, post);
    }

    @Override
    public boolean delete(User fromUser, Post post) {
        Like like = likeRepository.findLikeByUserAndPost(fromUser, post);
        if (like!=null) {
            likeRepository.delete(like);
            return true;
        }
        return false;
    }

    @Override
    public int getCountLike(Post post){
        if(likeRepository.findAllLikeByPost(post.getId()) == null)
            return 0;
        return likeRepository.findAllLikeByPost(post.getId()).size();
    }

    @Override
    public boolean checkLike(User fromUser, Post post){
        return likeRepository.findLikeByUserAndPost(fromUser, post) != null;
    }
}
