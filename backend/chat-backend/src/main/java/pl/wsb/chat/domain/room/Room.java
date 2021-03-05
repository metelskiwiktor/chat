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

    public Room(String id, String name, List<RoomMessage> messages) {
        this.id = id;
        this.name = name;
        this.messages = messages;
    }

    public Room(String name, List<RoomMessage> messages) {
        this.name = name;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<RoomMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", messages=" + messages +
                '}';
    }
}
