package pl.wsb.chat.infrastructure.local.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.pm.PrivateMessage;
import pl.wsb.chat.domain.pm.PrivateMessageRepository;

public interface PrivateMessageRepositoryLocal extends PrivateMessageRepository, MongoRepository<PrivateMessage, String> {
}
