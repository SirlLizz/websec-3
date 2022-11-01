package com.example.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "auth_user", schema = "public")
public class AuthUser {
    @Id
    @Column(name = "id")
    private UUID id;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="\"user\"")
    private User User;
    @Column(name = "browser")
    private String browser;
    @Column(name = "ip_user")
    private String ip;

    public AuthUser() {
        id = UUID.randomUUID();
    }

    public AuthUser(User user, String browser, String ip) {
        id = UUID.randomUUID();
        User = user;
        this.browser = browser;
        this.ip = ip;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public com.example.server.model.User getUser() {
        return User;
    }

    public void setUser(com.example.server.model.User user) {
        User = user;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                ", User=" + User +
                ", browser='" + browser + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
