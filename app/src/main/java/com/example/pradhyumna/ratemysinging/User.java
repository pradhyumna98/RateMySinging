package com.example.pradhyumna.ratemysinging;

public class User {
    int commentCount;
    String name;

    public User() {
    }

    public User(int commentCount, String name) {
        this.commentCount = commentCount;
        this.name = name;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
