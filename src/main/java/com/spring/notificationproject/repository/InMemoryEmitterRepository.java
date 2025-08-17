package com.spring.notificationproject.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void add(String userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public SseEmitter get(String userId) {
        return emitters.get(userId);
    }

    public void remove(String userId) {
        emitters.remove(userId);
    }

    public Set<String> getAllUserIds() {
        return emitters.keySet();
    }
}
