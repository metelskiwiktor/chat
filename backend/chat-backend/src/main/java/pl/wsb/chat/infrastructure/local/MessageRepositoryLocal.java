package pl.wsb.chat.infrastructure.local;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.message.Message;
import pl.wsb.chat.domain.message.MessageRepository;

public interface MessageRepositoryLocal extends MessageRepository, MongoRepository<Message, String> {

}
