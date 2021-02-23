package pl.wsb.chat.domain.message;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
public class Message {
    @Id
    private String id;
    private String note;
    private LocalDateTime date;

    public Message() {
    }

    public Message(String note, LocalDateTime date) {
        this.note = note;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
