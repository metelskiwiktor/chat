package pl.wsb.chat.domain.pm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wsb.chat.domain.user.User;

import java.time.LocalDateTime;

@Document
public class PrivateMessage {
    @Id
    private String id;
    private User from;
    private User to;
    private String message;
    private LocalDateTime createdAt;

    public PrivateMessage() {
    }

    public PrivateMessage(User from, User to, String message, LocalDateTime createdAt) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
