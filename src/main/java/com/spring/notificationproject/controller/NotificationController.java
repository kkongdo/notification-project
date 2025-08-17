package com.spring.notificationproject.controller;

import com.spring.notificationproject.repository.InMemoryEmitterRepository;
import com.spring.notificationproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;
    private final InMemoryEmitterRepository repository;

    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        repository.add(userId, emitter);

        emitter.onCompletion(() -> repository.remove(userId));
        emitter.onTimeout(() -> repository.remove(userId));

        try {
            emitter.send(SseEmitter.event().name("connect").data("connected to user: " + userId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam String to, @RequestParam String message) {
        service.send(to, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/emitters")
    public ResponseEntity<Set<String>> listConnectedUsers() {
        return ResponseEntity.ok(repository.getAllUserIds());
    }
}
