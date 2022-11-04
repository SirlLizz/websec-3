package com.example.server.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "posts", schema = "public")
public class Post {
    @Id
    @Column(name = "id")
    private UUID id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="\"user\"")
    private User user;
    @Column(name = "lend")
    private String lend;
    @Column(name = "photo")
    private String photo;
    @Column(name = "count_like")
    private int countLike;
    @Column(name = "date")
    private Date date;

    public Post(User user, String lend, String photo, int countLike) {
        id = UUID.randomUUID();
        this.user = user;
        this.lend = lend;
        this.photo = photo;
        this.countLike = countLike;
        this.date = new Date();
    }

    public Post() {
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

    public String getLend() {
        return lend;
    }

    public void setLend(String lend) {
        this.lend = lend;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", lend='" + lend + '\'' +
                ", photo=" + photo +
                ", countLike=" + countLike +
                ", date=" + date +
                '}';
    }
}
