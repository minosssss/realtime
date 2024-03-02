package com.realtime.listener;

import com.realtime.model.ActivityType;
import com.realtime.model.UserActivity;
import com.realtime.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final UserActivityService userActivityService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(UserActivityService userActivityService, SimpMessageSendingOperations messagingTemplate) {
        this.userActivityService = userActivityService;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String username = headerAccessor.getUser().getName(); // 실제 사용자 이름을 추출하는 방식은 애플리케이션의 인증 메커니즘에 따라 다름
        userActivityService.registerUserSession(sessionId, username);
        messagingTemplate.convertAndSend("/topic/userActivity", new UserActivity(username, ActivityType.CONNECTED, null));
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String username = userActivityService.getUsernameForSessionId(sessionId); // 사용자 이름을 세션 ID를 통해 조회
        userActivityService.removeUserSession(sessionId); // 세션 ID를 통해 사용자 세션 정보 제거
        messagingTemplate.convertAndSend("/topic/userActivity", new UserActivity(username, ActivityType.DISCONNECTED, null));
    }
}
