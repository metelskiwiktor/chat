package pl.wsb.chat.api.dto.response;

import java.util.List;

public class RoomView {
    private String id;
    private String name;
    private List<RoomMessageView> roomMessages;

    public RoomView(String id, String name, List<RoomMessageView> roomMessages) {
        this.id = id;
        this.name = name;
        this.roomMessages = roomMessages;
    }

    public RoomView() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RoomMessageView> getRoomMessages() {
        return roomMessages;
    }
}
