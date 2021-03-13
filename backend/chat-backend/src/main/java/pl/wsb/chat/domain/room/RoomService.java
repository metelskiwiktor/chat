package pl.wsb.chat.domain.room;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.wsb.chat.api.dto.request.AddMessageToRoom;
import pl.wsb.chat.api.dto.response.RoomMessageView;
import pl.wsb.chat.api.dto.response.RoomView;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserService;
import pl.wsb.chat.lib.Assertion;
import pl.wsb.chat.lib.BreakingCharactersUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final ConversionService conversionService;

    public RoomService(RoomRepository roomRepository, UserService userService, ConversionService conversionService) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.conversionService = conversionService;
    }

    public RoomView createRoom(String roomName) {
        roomName = BreakingCharactersUtils.replace(roomName);
        logger.info("Starting to create a room called '{}'", roomName);

        Validator.validateRoom(roomName);

        if (existRoomByName(roomName)) {
            throw new DomainException(ExceptionCode.ROOM_ALREADY_EXISTS, roomName);
        }

        Room room = roomRepository.save(new Room(
                roomName,
                new ArrayList<>()
        ));

        return conversionService.convert(room, RoomView.class);
    }

    public void addMessage(String roomId, AddMessageToRoom addMessageToRoom) {
        roomId = BreakingCharactersUtils.replace(roomId);
        String note = BreakingCharactersUtils.replace(addMessageToRoom.getNote());
        String userId = BreakingCharactersUtils.replace(addMessageToRoom.getUserId());
        logger.info("Starting to add a message (note='{}', author='{}') to a room with id '{}'",
                note, userId, roomId);

        Validator.validateMessage(note);
        User author = userService.getById(userId);
        RoomMessage roomMessage = new RoomMessage(
                ObjectId.get().toString(),
                note,
                LocalDateTime.now(),
                author
        );
        Room room = getRoomById(roomId);
        List<RoomMessage> messages = room.getMessages();
        messages.add(roomMessage);
        roomRepository.save(room);
    }

    public List<RoomMessageView> getMessages(String roomId) {
        roomId = BreakingCharactersUtils.replace(roomId);
        logger.info("Starting to return all of messages from room id '{}'", roomId);

        Room room = getRoomById(roomId);

        return room.getMessages().stream()
                .map(roomMessage -> conversionService.convert(roomMessage, RoomMessageView.class))
                .collect(Collectors.toList());
    }

    public void deleteMessage(String message) {
        String messageId = BreakingCharactersUtils.replace(message);
        logger.info("Starting to delete a message with id '{}'", messageId);

        Room room = roomRepository.findAll().stream()
                .filter(r -> r.getMessages().stream()
                        .anyMatch(roomMessage -> roomMessage.getId().equals(messageId)))
                .findFirst()
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_MESSAGE, messageId));

        List<RoomMessage> roomMessages = room.getMessages().stream()
                .filter(rm -> !rm.getId().equals(messageId))
                .collect(Collectors.toList());
        room.setMessages(roomMessages);

        roomRepository.save(room);
    }

    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_ROOM, roomId));
    }

    public boolean existRoomByName(String roomName) {
        return roomRepository.existsByName(roomName);
    }

    private static class Validator {
        static void validateRoom(String roomName) {
            Assertion.notEmpty(roomName, () -> new DomainException(ExceptionCode.ROOM_HAS_BLANK_NAME));
        }

        static void validateMessage(String note) {
            Assertion.notEmpty(note, () -> new DomainException(ExceptionCode.INVALID_MESSAGE));
        }
    }
}
