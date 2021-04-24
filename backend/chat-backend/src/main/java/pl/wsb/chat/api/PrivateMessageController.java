package pl.wsb.chat.api;

import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.api.dto.request.SendPrivateMessageRequest;
import pl.wsb.chat.api.dto.response.PrivateMessageView;
import pl.wsb.chat.domain.pm.PrivateMessageService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/message/private")
public class PrivateMessageController {
    private final PrivateMessageService privateMessageService;

    public PrivateMessageController(PrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @PostMapping
    public void addPrivateMessage(@RequestBody SendPrivateMessageRequest sendPrivateMessageRequest) {
        privateMessageService.addMessage(sendPrivateMessageRequest);
    }

    @GetMapping("/conversations")
    public List<PrivateMessageView> getConversations() {
        return privateMessageService.getConversations();
    }
}
