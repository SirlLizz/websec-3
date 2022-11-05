package com.example.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "like", schema = "public")
public class Like {
    @Id
    @Column(name = "id")
    private UUID id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="from_user")
    private User user;
    @ManyToOne(targetEntity=Post.class)
    @JoinColumn(name="post")
    private Post post;

    public Like(User user, Post post) {
        id = UUID.randomUUID();
        this.user = user;
        this.post = post;
    }

    public Like() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
