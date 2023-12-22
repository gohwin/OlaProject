package com.ola.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    private String roomId;
	
    private String name;
    
    @Builder.Default
    private Set<WebSocketSession> sessions = new HashSet<>();
}