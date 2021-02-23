package pl.wsb.chat.domain.message;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

@Document
public class Message {
    @Id
    private ObjectId id;
    private String note;
    private LocalDateTime date;

    public Message() {
    }

    public Message(String notes, LocalDateTime date) {
        this.note = notes;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
                ", notes='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
