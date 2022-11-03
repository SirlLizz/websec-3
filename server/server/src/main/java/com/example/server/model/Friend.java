package com.example.server.model;

public class Friend {
    String name;
    Boolean follow;

    public Friend(String name, Boolean follow) {
        this.name = name;
        this.follow = follow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }
}
