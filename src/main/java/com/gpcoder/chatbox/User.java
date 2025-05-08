package com.gpcoder.chatbox;

public class User {
    private String name;
    private String avatarPath;

    public User(String name, String avatarPath) {
        this.name = name;
        this.avatarPath = avatarPath;
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
}
