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

//    @GetMapping("/conversation")
//    public List<PrivateMessage> getPrivateMessages(@RequestParam("recipient-id") String recipientId) {
//        return privateMessageService.getMessages(recipientId);
//    }

    @GetMapping("/conversations")
    public List<PrivateMessageView> getConversations() {
        PrivateMessageView privateMessageView1 = new PrivateMessageView("1234rec", "asd", "wiadomosc", LocalDateTime.now());
        PrivateMessageView privateMessageView2 = new PrivateMessageView("1234rec", "asd", "zxczx", LocalDateTime.now());
        PrivateMessageView privateMessageView3 = new PrivateMessageView("1234rec", "asd", "bcv", LocalDateTime.now());
        PrivateMessageView privateMessageView4 = new PrivateMessageView("1234rec", "asd", "zxc", LocalDateTime.now());
        PrivateMessageView privateMessageView5 = new PrivateMessageView("3453", "asd", "axdasd", LocalDateTime.now());
        PrivateMessageView privateMessageView6 = new PrivateMessageView("12312", "asd", "asdaz", LocalDateTime.now());

        return Arrays.asList(privateMessageView1, privateMessageView2, privateMessageView3, privateMessageView4, privateMessageView5, privateMessageView6);
//        return privateMessageService.getConversations();
    }
}
