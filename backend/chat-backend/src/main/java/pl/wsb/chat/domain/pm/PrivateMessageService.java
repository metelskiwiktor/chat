package pl.wsb.chat.domain.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserService;
import pl.wsb.chat.lib.BreakingCharactersUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivateMessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final PrivateMessageRepository privateMessageRepository;

    public PrivateMessageService(UserService userService, PrivateMessageRepository privateMessageRepository) {
        this.userService = userService;
        this.privateMessageRepository = privateMessageRepository;
    }

    public void addMessage(PrivateMessage privateMessage) {
        String from = BreakingCharactersUtils.replace(privateMessage.getFrom().getId());
        String to = BreakingCharactersUtils.replace(privateMessage.getTo().getId());
        String message = BreakingCharactersUtils.replace(privateMessage.getMessage());
        logger.info("Starting to add private message (from='{}', to='{}', message='{}')",
                from, to, message);

        privateMessageRepository.save(privateMessage);
    }

    public List<PrivateMessage> getMessages(String recipientId) {
        recipientId = BreakingCharactersUtils.replace(recipientId);
        logger.info("Started to get all of private messages from recipientId='{}'", recipientId);

        User user = userService.getById(recipientId);
        return privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());
    }

    public List<Conversation> getConversations(String userId) {
        userId = BreakingCharactersUtils.replace(userId);
        logger.info("Started to get all of conversations for user='{}'", userId);

        User user = userService.getById(userId);
        List<Conversation> conversations = new ArrayList<>();
        List<PrivateMessage> privateMessages = privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());

        for (PrivateMessage privateMessage : privateMessages) {
            if (conversationsContainsRecipient(conversations, privateMessage.getTo())) {
                continue;
            }

            conversations.add(new Conversation(privateMessage.getTo(), privateMessage.getMessage()));
        }

        return conversations;
    }

    private boolean conversationsContainsRecipient(List<Conversation> conversations, User recipient) {
        return conversations.stream()
                .anyMatch(conversation -> conversation.getFrom().equals(recipient));
    }
}
