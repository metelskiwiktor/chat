package pl.wsb.chat.domain.user;

import org.springframework.stereotype.Service;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_USER, userId));
    }
}
