package pl.wsb.chat.domain.pm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wsb.chat.domain.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateMessage that = (PrivateMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(message, that.message) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, message, createdAt);
    }
}
