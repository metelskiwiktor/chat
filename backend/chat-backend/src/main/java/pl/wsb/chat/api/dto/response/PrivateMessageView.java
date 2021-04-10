package pl.wsb.chat.api.dto.response;

import java.time.LocalDateTime;

public class PrivateMessageView {
    private String fromUsername;
    private String toUsername;
    private String message;
    private LocalDateTime date;

    public PrivateMessageView(String fromUsername, String toUsername, String message, LocalDateTime date) {
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.message = message;
        this.date = date;
    }

    public PrivateMessageView() {
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
