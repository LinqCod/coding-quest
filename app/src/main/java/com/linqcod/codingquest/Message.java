package com.linqcod.codingquest;

public class Message {

    private String content;
    private int imageUri;
    private int userId;
    private boolean hasImage;

    public Message(String content, int imageUri, int userId, boolean hasImage) {
        this.content = content;
        this.imageUri = imageUri;
        this.userId = userId;
        this.hasImage = hasImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}
