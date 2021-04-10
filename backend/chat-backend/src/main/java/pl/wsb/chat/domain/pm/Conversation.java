package pl.wsb.chat.domain.pm;

import pl.wsb.chat.domain.user.User;

import java.util.List;

public class Conversation {
    private final User recipient;
    private final List<PrivateMessage> privateMessages;

    public Conversation(User recipient, List<PrivateMessage> privateMessages) {
        this.recipient = recipient;
        this.privateMessages = privateMessages;
    }

    public User getRecipient() {
        return recipient;
    }

    public List<PrivateMessage> getPrivateMessages() {
        return privateMessages;
    }
}
