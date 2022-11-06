package com.example.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts", schema = "public")
public class Post implements Comparable<Post>{
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
    @Column(name = "date")
    private LocalDateTime date;

    public Post(User user, String lend, String photo) {
        id = UUID.randomUUID();
        this.user = user;
        this.lend = lend;
        this.photo = photo;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", lend='" + lend + '\'' +
                ", photo=" + photo +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Post post) {
        LocalDateTime otherDate = post.getDate();
        int cmp = (otherDate.getYear() -this.date.getYear());
        if (cmp == 0) {
            cmp = (otherDate.getMonthValue() - this.date.getMonthValue());
            if (cmp == 0) {
                cmp = (otherDate.getDayOfMonth() - this.date.getDayOfMonth());
                if (cmp == 0) {
                    cmp = ( otherDate.getHour() - this.date.getHour());
                    if (cmp == 0) {
                        cmp = (otherDate.getMinute() - this.date.getMinute());
                        if (cmp == 0) {
                            cmp = (otherDate.getSecond() - this.date.getSecond());
                        }
                    }
                }
            }
        }
        return cmp;
    }
}
