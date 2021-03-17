package pl.wsb.chat.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

import java.util.Optional;

public interface UserRepositoryMongoDB extends UserRepository, MongoRepository<User, String> {
    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
