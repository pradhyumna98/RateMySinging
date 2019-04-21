package com.example.pradhyumna.ratemysinging;

public class Comment {
    String commentText;
    String userId;
    String dateOfComment;

    public Comment() {
    }

    public Comment(String commentText, String userId, String dateOfComment) {
        this.commentText = commentText;
        this.userId = userId;
        this.dateOfComment = dateOfComment;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(String dateOfComment) {
        this.dateOfComment = dateOfComment;
    }
}
