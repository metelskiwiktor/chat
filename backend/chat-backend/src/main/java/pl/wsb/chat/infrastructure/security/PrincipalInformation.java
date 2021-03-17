package pl.wsb.chat.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;
import pl.wsb.chat.domain.user.UserRepository;

@Component
public class PrincipalInformation {
    private final UserRepository userRepository;

    public PrincipalInformation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getUserId() {
        return userRepository.findByLogin(getUsername())
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_USER, getUsername()))
                .getId();
    }

    public String getUserId(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_USER, getUsername()))
                .getId();
    }
}
