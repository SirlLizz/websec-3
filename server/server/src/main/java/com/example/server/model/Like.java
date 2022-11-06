package com.example.server.model;

import javax.persistence.*;

@Entity
@Table(name = "like", schema = "public")
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="from_user")
    private User user;
    @ManyToOne(targetEntity=Post.class)
    @JoinColumn(name="post")
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Like() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
