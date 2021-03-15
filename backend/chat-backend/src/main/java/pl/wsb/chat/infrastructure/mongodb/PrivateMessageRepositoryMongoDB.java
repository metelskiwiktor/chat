package pl.wsb.chat.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.pm.PrivateMessage;
import pl.wsb.chat.domain.pm.PrivateMessageRepository;

public interface PrivateMessageRepositoryMongoDB extends PrivateMessageRepository, MongoRepository<PrivateMessage, String> {
}
