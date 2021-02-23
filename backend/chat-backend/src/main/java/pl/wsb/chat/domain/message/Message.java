package pl.wsb.chat.domain.message;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Message {
    @Id
    private ObjectId id;
    private String notes;
    private Date date;

    public Message() {
    }

    public Message(String notes, Date date) {
        this.notes = notes;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                '}';
    }
}
