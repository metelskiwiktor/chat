package pl.wsb.chat.api.dto.response;

public class UserProfileView {
    private String id;
    private String username;
    private int roomMessages;

    public UserProfileView() {
    }

    public UserProfileView(String id, String username, int roomMessages) {
        this.id = id;
        this.username = username;
        this.roomMessages = roomMessages;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getRoomMessages() {
        return roomMessages;
    }
}
