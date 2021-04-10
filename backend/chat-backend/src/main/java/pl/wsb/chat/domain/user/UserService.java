package pl.wsb.chat.domain.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsb.chat.api.dto.request.RegisterUserRequest;
import pl.wsb.chat.api.dto.response.UserProfileView;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;
import pl.wsb.chat.domain.room.RoomService;
import pl.wsb.chat.lib.Assertion;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoomService roomService;

    public UserService(UserRepository userRepository, @Lazy RoomService roomService) {
        this.userRepository = userRepository;
        this.roomService = roomService;
    }

    public User getById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_USER_ID, userId));
    }

    public User getByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_USER_LOGIN, username));
    }

    public void register(RegisterUserRequest registerUserRequest, Set<Role> roles) {
        if (userRepository.existsByLogin(registerUserRequest.getLogin())) {
            throw new DomainException(ExceptionCode.USER_ALREADY_EXISTS, registerUserRequest.getLogin());
        }

        Validator.validateLoginAndPassword(registerUserRequest.getLogin(), registerUserRequest.getPassword());

        String hashedPassword = new BCryptPasswordEncoder().encode(registerUserRequest.getPassword());

        User user = new User(
                registerUserRequest.getLogin(),
                hashedPassword,
                roles
        );

        userRepository.save(user);
    }

    public UserProfileView getUserProfile(String userName) {
        User user = getByUsername(userName);
        int roomMessages = roomService.countMessagesByUser(user);
        return new UserProfileView(user.getId(), user.getLogin(), roomMessages);
    }

    private static class Validator {
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        private static final int LOGIN_MINIMUM_LENGTH = 3;
        private static final int PASSWORD_MINIMUM_LENGTH = 5;

        static void validateLoginAndPassword(String login, String password) {
            Assertion.isBiggerEqualsThan(LOGIN_MINIMUM_LENGTH, login,
                    () -> new DomainException(ExceptionCode.FIELD_IS_TOO_SHORT, LOGIN));

            Assertion.isBiggerEqualsThan(PASSWORD_MINIMUM_LENGTH, password,
                    () -> new DomainException(ExceptionCode.FIELD_IS_TOO_SHORT, PASSWORD));
        }
    }
}
