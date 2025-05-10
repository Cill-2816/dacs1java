package com.gpcoder.chatbox;

public class User {
    private String username;
    private String name;
    private String avatarPath;

    public User() {
    }

    public User(String username, String name, String avatarPath) {
        this.avatarPath = avatarPath;
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    @Override
    public String toString() {
        return name; // để hiển thị đúng tên nếu JList gọi toString()
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
