package pl.wsb.chat.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wsb.chat.api.dto.response.UserView;
import pl.wsb.chat.domain.user.User;

@Component
public class UserToUserViewConverter implements Converter<User, UserView> {

    @Override
    public UserView convert(User user) {
        return new UserView(
                user.getId(),
                user.getLogin()
        );
    }
}
