package com.realtime.controller;

import com.realtime.model.ChatMessage;
import com.realtime.model.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/userActivity")
    public void handleUserActivity(UserActivity message) {
        messagingTemplate.convertAndSend("/topic/userActivity", message);
    }
}