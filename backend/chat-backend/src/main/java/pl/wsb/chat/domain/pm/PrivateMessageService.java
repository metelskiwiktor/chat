package pl.wsb.chat.domain.pm;

import org.springframework.stereotype.Service;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivateMessageService {
    private final UserService userService;
    private final PrivateMessageRepository privateMessageRepository;

    public PrivateMessageService(UserService userService, PrivateMessageRepository privateMessageRepository) {
        this.userService = userService;
        this.privateMessageRepository = privateMessageRepository;
    }

    public void addMessage(PrivateMessage privateMessage) {
        privateMessageRepository.save(privateMessage);
    }

    public List<PrivateMessage> getMessages(String recipient) {
        User user = userService.getById(recipient);
        return privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());
    }

    public List<Conversation> getConversations(String userId) {
        User user = userService.getById(userId);
        List<Conversation> conversations = new ArrayList<>();
        List<PrivateMessage> privateMessages = privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());

        for(PrivateMessage privateMessage: privateMessages){
            if (conversationsContainsRecipient(conversations, privateMessage.getTo())){
                continue;
            }

            conversations.add(new Conversation(privateMessage.getTo(), privateMessage.getMessage()));
        }

        return conversations;
    }

    private boolean conversationsContainsRecipient(List<Conversation> conversations, User recipient){
        return conversations.stream()
                .anyMatch(conversation -> conversation.getFrom().equals(recipient));
    }
}
