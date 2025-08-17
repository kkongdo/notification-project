package com.spring.notificationproject.service;

import com.spring.notificationproject.repository.InMemoryEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final InMemoryEmitterRepository repository;

    public void send(String receiverId, String content) {
        SseEmitter emitter = repository.get(receiverId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(content));
            } catch (IOException e) {
                repository.remove(receiverId);
            }
        }
    }
}
