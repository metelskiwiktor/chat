package pl.wsb.chat.domain.message;

import java.util.List;

public interface MessageRepository {

    Message save(Message message);

    List<Message> findAll();
}
