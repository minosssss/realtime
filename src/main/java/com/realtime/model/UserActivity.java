package com.realtime.model;

public class UserActivity {
    private String username;
    private ActivityType type;
    private Object data; // 이 필드는 마우스 포인터의 위치나 다른 활동 데이터를 포함할 수 있습니다.

    public UserActivity() {
    }

    public UserActivity(String username, ActivityType type, Object data) {
        this.username = username;
        this.type = type;
        this.data = data;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
