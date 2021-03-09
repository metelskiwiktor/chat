package pl.wsb.chat.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
    Optional<User> findById(String userId);
}
