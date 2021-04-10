package pl.wsb.chat.domain.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.wsb.chat.api.dto.request.SendPrivateMessageRequest;
import pl.wsb.chat.api.dto.response.PrivateMessageView;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserService;
import pl.wsb.chat.infrastructure.security.PrincipalInformation;
import pl.wsb.chat.lib.BreakingCharactersUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivateMessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final PrivateMessageRepository privateMessageRepository;
    private final PrincipalInformation principalInformation;
    private final ConversionService conversionService;

    public PrivateMessageService(UserService userService, PrivateMessageRepository privateMessageRepository,
                                 PrincipalInformation principalInformation, ConversionService conversionService) {
        this.userService = userService;
        this.privateMessageRepository = privateMessageRepository;
        this.principalInformation = principalInformation;
        this.conversionService = conversionService;
    }

    public void addMessage(SendPrivateMessageRequest sendPrivateMessageRequest) {
        String from = principalInformation.getUserId();
        String to = BreakingCharactersUtils.replace(sendPrivateMessageRequest.getRecipientId());
        String message = BreakingCharactersUtils.replace(sendPrivateMessageRequest.getMessage());
        logger.info("Starting to add private message (from='{}', to='{}', message='{}')",
                from, to, message);

        User userFrom = userService.getById(from);
        User userTo = userService.getById(to);

        privateMessageRepository.save(new PrivateMessage(
                userFrom,
                userTo,
                message,
                LocalDateTime.now()
        ));
    }

    public List<PrivateMessage> getMessages(String recipientId) {
        recipientId = BreakingCharactersUtils.replace(recipientId);
        logger.info("Started to get all of private messages from recipientId='{}'", recipientId);

        User user = userService.getById(recipientId);
        return privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());
    }

    public List<PrivateMessageView> getConversations() {
        String userId = principalInformation.getUserId();
        logger.info("Started to get all of conversations for user='{}'", userId);

        User user = userService.getById(userId);
        List<PrivateMessage> privateMessages = privateMessageRepository.findAll().stream()
                .filter(privateMessage -> privateMessage.getFrom().equals(user))
                .collect(Collectors.toList());

        return  privateMessages.stream()
                .map(privateMessage -> conversionService.convert(privateMessage, PrivateMessageView.class))
                .collect(Collectors.toList());
    }
}
