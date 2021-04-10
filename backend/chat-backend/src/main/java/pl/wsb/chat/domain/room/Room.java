package pl.wsb.chat.domain.room;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Room {
    @Id
    private String id;
    private String name;
    private List<RoomMessage> messages;

    public Room() {
    }

    public Room(String name, List<RoomMessage> messages) {
        this.name = name;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RoomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<RoomMessage> messages) {
        this.messages = messages;
    }
}
