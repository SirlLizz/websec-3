package com.example.server.model;

import javax.persistence.*;

@Entity
@Table(name = "follow", schema = "public")
public class Follow {
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="from_user")
    private User fromUser;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="to_user")
    private User toUser;

    public Follow(int id, User fromUser, User toUser) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public Follow() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                '}';
    }
}