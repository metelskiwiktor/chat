package pl.wsb.chat.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wsb.chat.api.dto.response.RoomMessageView;
import pl.wsb.chat.api.dto.response.UserView;
import pl.wsb.chat.domain.room.RoomMessage;

@Component
public class RoomMessageToRoomMessageViewConverter implements Converter<RoomMessage, RoomMessageView> {
    private final ConversionService conversionService;

    @Lazy
    public RoomMessageToRoomMessageViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public RoomMessageView convert(RoomMessage roomMessage) {
        UserView author = conversionService.convert(roomMessage.getAuthor(), UserView.class);

        return new RoomMessageView(
                roomMessage.getId(),
                roomMessage.getNote(),
                roomMessage.getCreatedAt(),
                author
        );
    }
}
