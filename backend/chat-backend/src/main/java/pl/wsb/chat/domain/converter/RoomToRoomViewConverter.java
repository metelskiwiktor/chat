package pl.wsb.chat.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wsb.chat.api.dto.response.RoomMessageView;
import pl.wsb.chat.api.dto.response.RoomView;
import pl.wsb.chat.domain.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RoomToRoomViewConverter implements Converter<Room, RoomView> {
    private final ConversionService conversionService;

    @Lazy
    public RoomToRoomViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public RoomView convert(Room room) {
        List<RoomMessageView> roomMessageViews = new ArrayList<>();

        if (!Objects.isNull(room.getMessages())) {
            roomMessageViews = room.getMessages().stream()
                    .map(roomMessage -> conversionService.convert(roomMessage, RoomMessageView.class))
                    .collect(Collectors.toList());
        }

        return new RoomView(
                room.getId(),
                room.getName(),
                roomMessageViews
        );
    }
}
