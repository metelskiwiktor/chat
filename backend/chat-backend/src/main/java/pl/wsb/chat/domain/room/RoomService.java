package pl.wsb.chat.domain.room;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.wsb.chat.api.dto.response.RoomMessageView;
import pl.wsb.chat.api.dto.response.RoomView;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserService;
import pl.wsb.chat.infrastructure.security.PrincipalInformation;
import pl.wsb.chat.lib.Assertion;
import pl.wsb.chat.lib.BreakingCharactersUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoomService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final ConversionService conversionService;
    private final PrincipalInformation principalInformation;

    public RoomService(RoomRepository roomRepository, UserService userService, ConversionService conversionService,
                       PrincipalInformation principalInformation) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.conversionService = conversionService;
        this.principalInformation = principalInformation;
    }

    public RoomView createRoom(String roomName) {
        Validator.validateRoom(roomName);

        roomName = BreakingCharactersUtils.replace(roomName);
        logger.info("Starting to create a room called '{}'", roomName);

        if (existRoomByName(roomName)) {
            throw new DomainException(ExceptionCode.ROOM_ALREADY_EXISTS, roomName);
        }

        Room room = roomRepository.save(new Room(
                roomName,
                new ArrayList<>()
        ));

        return conversionService.convert(room, RoomView.class);
    }

    public void addMessage(String roomId, String message) {
        Validator.validateMessage(message);

        roomId = BreakingCharactersUtils.replace(roomId);
        String note = BreakingCharactersUtils.replace(message);
        String userId = principalInformation.getUserId();
        logger.info("Starting to add a message (note='{}', author='{}') to a room with id '{}'",
                note, userId, roomId);

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
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(RoomMessageView::getDate).reversed())
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

    public List<RoomView> getRooms() {
        return roomRepository.findAll().stream()
                .map(room -> conversionService.convert(room, RoomView.class))
                .collect(Collectors.toList());
    }

    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_ROOM, roomId));
    }

    public boolean existRoomByName(String roomName) {
        return roomRepository.existsByName(roomName);
    }

    public int countMessagesByUser(User user) {
        return (int) roomRepository.findAll().stream()
                .flatMap(room -> Stream.of(room.getMessages()))
                .flatMap(List::stream)
                .filter(roomMessage -> roomMessage.getAuthor().getId().equals(user.getId()))
                .count();
    }

    private static class Validator {
        public static final String ROOM = "rom";
        public static final String MESSAGE = "message";
        public static final int ROOM_MINIMUM_SIZE = 5;
        public static final int NOTE_MINIMUM_SIZE = 1;

        static void validateRoom(String roomName) {
            Assertion.isBiggerEqualsThan(ROOM_MINIMUM_SIZE, roomName,
                    () -> new DomainException(ExceptionCode.FIELD_IS_TOO_SHORT, ROOM));
        }

        static void validateMessage(String note) {
            Assertion.isBiggerEqualsThan(NOTE_MINIMUM_SIZE, note,
                    () -> new DomainException(ExceptionCode.FIELD_IS_TOO_SHORT, MESSAGE));
        }
    }
}
