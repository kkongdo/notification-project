# notification-project
실시간 알림 기능 토이 프로젝트

# 🔔 Real-Time Notification System (SSE 기반)

간단한 **Spring Boot + Server-Sent Events (SSE)** 기반의 실시간 알림 시스템입니다.  
유저가 SSE로 서버에 연결되면, 서버는 이벤트 발생 시 해당 유저에게 **실시간으로 알림을 전송**합니다.

---

## 📦 기술 스택

- Java 17+
- Spring Boot 3.x
- Spring Web (SSE)
- Lombok
- Git Bash / curl / Postman (테스트 도구)

---

## 🚀 기능 요약

| 기능 | 설명 |
|------|------|
| 📡 실시간 연결 | 유저가 `/subscribe/{userId}` 로 SSE 연결 |
| 📬 알림 전송 | `/send?to=userId&message=xxx` 로 알림 전송 |
| 👀 연결 상태 확인 | `/emitters` 로 현재 SSE 연결된 유저 조회 가능 |

---

## ✅ 실행 방법

### 1. 프로젝트 실행
```bash
./gradlew bootRun
# 또는
mvn spring-boot:run
```

### 2. SSE 연결 테스트 (Git Bash)
```bash
curl http://localhost:8080/notifications/subscribe/userA
```
### 3. 알림 전송 (다른 터미널 or Postman)
```bash
curl -X POST "http://localhost:8080/notifications/send?to=userA&message=Hello+userA"
```
### 4. 연결 유저 목록 확인
```bash
curl http://localhost:8080/notifications/emitters
```

📂 API 정리

📡 GET 
```url
/notifications/subscribe/{userId}
```
SSE 연결, text/event-stream 형식으로 연결 유지

📨 POST

```url
/notifications/send
```
Query Params: to는 알림 수신자 ID, message는 전송할 메시지

🧾 GET 
```url
/notifications/emitters
```
현재 연결된 유저 ID 목록 반환

---
## 💡 향후 계획
- 알림 DB 저장 (읽음/안읽음)
- JWT 기반 사용자 인증 연동
- 프론트 UI 연동 (Vue/React)
- Redis Pub/Sub 기반 멀티 인스턴스 확장
- WebSocket 기반 알림으로 전환
