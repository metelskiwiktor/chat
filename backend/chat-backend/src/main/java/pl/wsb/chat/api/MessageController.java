package pl.wsb.chat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.domain.message.Message;
import pl.wsb.chat.domain.message.MessageRepository;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("messages")
    public List<Message> getMessage() {
        return messageRepository.findAll();
    }

    @PostMapping("message")
    public List<Message> saveMessage(@RequestBody Message message) {
        message.setDate(new Date());
        messageRepository.save(message);
        return messageRepository.findAll();
    }
}
