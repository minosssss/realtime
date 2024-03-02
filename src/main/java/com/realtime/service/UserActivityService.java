package com.realtime.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserActivityService {
    // 현재 연결된 사용자 목록과 활동 상태를 관리하는 로직
    // 예: addUser(), removeUser(), getUserActivity()
    private final Map<String, String> userSessions = new ConcurrentHashMap<>();

    public void registerUserSession(String sessionId, String username) {
        userSessions.put(sessionId, username);
    }

    public void removeUserSession(String sessionId) {
        userSessions.remove(sessionId);
    }

    public String getUsernameForSessionId(String sessionId) {
        return userSessions.get(sessionId);
    }
}
