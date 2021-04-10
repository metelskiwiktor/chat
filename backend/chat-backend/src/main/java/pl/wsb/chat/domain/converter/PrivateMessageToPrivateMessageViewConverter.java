package pl.wsb.chat.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wsb.chat.api.dto.response.PrivateMessageView;
import pl.wsb.chat.domain.pm.PrivateMessage;

@Component
public class PrivateMessageToPrivateMessageViewConverter implements Converter<PrivateMessage, PrivateMessageView> {

    @Override
    public PrivateMessageView convert(PrivateMessage privateMessage) {
        return new PrivateMessageView(
                privateMessage.getFrom().getLogin(),
                privateMessage.getTo().getLogin(),
                privateMessage.getMessage(),
                privateMessage.getCreatedAt());
    }
}
