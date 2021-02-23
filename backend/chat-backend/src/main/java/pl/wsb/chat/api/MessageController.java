package pl.wsb.chat.api;


import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.domain.message.Message;
import pl.wsb.chat.domain.message.MessageRepository;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/list")
    public List<Message> getMessage() {
        return messageRepository.findAll();
    }

    @PostMapping("/save")
    public List<Message> saveMessage(@RequestBody Message message) {
        message.setDate(LocalDateTime.now());
        messageRepository.save(message);
        return messageRepository.findAll();
    }
}
