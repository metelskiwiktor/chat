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

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getAuthor() {
        return author;
    }
}
