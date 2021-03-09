package pl.wsb.chat.domain.room;

import org.springframework.stereotype.Service;
import pl.wsb.chat.domain.exception.DomainException;
import pl.wsb.chat.domain.exception.ExceptionCode;
import pl.wsb.chat.infrastructure.local.repository.RoomRepositoryLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String roomName) {
        if(existRoomByName(roomName)) {
            throw new DomainException(ExceptionCode.ROOM_ALREADY_EXIST, roomName);
        }

        return roomRepository.save(new Room(
                roomName,
                new ArrayList<>()
        ));
    }

    public void addMessage(String roomId, RoomMessage roomMessage) {
        Room room = getRoomById(roomId);
        List<RoomMessage> messages = room.getMessages();
        messages.add(roomMessage);
        roomRepository.save(room);
    }

    public List<RoomMessage> getMessages(String roomId) {
        Room room = getRoomById(roomId);
        return room.getMessages();
    }

    public void deleteMessage(String roomId, String messageId) {
        Room room = getRoomById(roomId);
        List<RoomMessage> roomMessages = room.getMessages().stream()
                .filter(rm -> !rm.getId().equals(messageId))
                .collect(Collectors.toList());
        room.setMessages(roomMessages);
        roomRepository.save(room);
    }

    public Room getRoomById(String roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DomainException(ExceptionCode.NO_SUCH_ROOM, roomId));
    }

    public boolean existRoomByName(String roomName){
        return roomRepository.existsByName(roomName);
    }
}
