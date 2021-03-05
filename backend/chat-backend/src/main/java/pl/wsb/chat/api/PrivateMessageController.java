package pl.wsb.chat.api;

import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.domain.pm.Conversation;
import pl.wsb.chat.domain.pm.PrivateMessage;
import pl.wsb.chat.domain.pm.PrivateMessageService;

import java.util.List;

@RestController
@RequestMapping("/message/private")
public class PrivateMessageController {
    private final PrivateMessageService privateMessageService;

    public PrivateMessageController(PrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @PostMapping
    public void addPrivateMessage(@RequestBody PrivateMessage privateMessage){
        privateMessageService.addMessage(privateMessage);
    }

    @GetMapping("/get-all")
    public List<PrivateMessage> getPrivateMessages(@RequestParam String recipient){
        return privateMessageService.getMessages(recipient);
    }

    @GetMapping("/conversations")
    public List<Conversation> getConversations(@RequestParam String userId){
        return privateMessageService.getConversations(userId);
    }
}
