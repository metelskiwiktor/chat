package pl.wsb.chat.domain.room;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wsb.chat.domain.user.User;

import java.time.LocalDateTime;

@Document
public class RoomMessage {
    @Id
    private String id;
    private String note;
    private LocalDateTime createdAt;
    private User author;

    public RoomMessage() {
    }

    public RoomMessage(String id, String note, LocalDateTime createdAt, User author) {
        this.id = id;
        this.note = note;
        this.createdAt = createdAt;
        this.author = author;
    }

    public RoomMessage(String note, LocalDateTime createdAt, User author) {
        this.note = note;
        this.createdAt = createdAt;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "RoomMessage{" +
                "id='" + id + '\'' +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                ", author=" + author +
                '}';
    }
}
