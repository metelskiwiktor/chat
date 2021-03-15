package pl.wsb.chat.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

public interface UserRepositoryAzureMongoDB extends UserRepository, MongoRepository<User, String> {
}
