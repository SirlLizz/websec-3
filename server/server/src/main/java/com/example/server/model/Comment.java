package com.example.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment", schema = "public")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="from_user")
    private User user;
    @ManyToOne(targetEntity=Post.class)
    @JoinColumn(name="post")
    private Post post;
    @Column(name = "text_com")
    private String text;
    @Column(name = "date")
    private LocalDateTime date;

    public Comment(User user, Post post, String text) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = LocalDateTime.now();
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}