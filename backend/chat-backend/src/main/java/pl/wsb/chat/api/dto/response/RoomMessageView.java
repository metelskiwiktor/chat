package pl.wsb.chat.api.dto.response;

import java.time.LocalDateTime;

public class RoomMessageView {
    private String id;
    private String note;
    private LocalDateTime date;
    private UserView author;

    public RoomMessageView(String id, String note, LocalDateTime date, UserView author) {
        this.id = id;
        this.note = note;
        this.date = date;
        this.author = author;
    }

    public RoomMessageView() {
    }

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UserView getAuthor() {
        return author;
    }
}
