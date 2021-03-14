package pl.wsb.chat.domain.pm;

import pl.wsb.chat.domain.user.User;

public class Conversation {
    private final User from;
    private final String lastMessageText;

    public Conversation(User from, String lastMessageText) {
        this.from = from;
        this.lastMessageText = lastMessageText;
    }

    public User getFrom() {
        return from;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }
}
