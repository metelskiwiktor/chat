package pl.wsb.chat.infrastructure.local.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

public interface UserRepositoryLocal extends UserRepository, MongoRepository<User, String> {
}
