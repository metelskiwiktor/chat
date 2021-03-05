package pl.wsb.chat.domain.room;

import org.springframework.stereotype.Service;

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
        return roomRepository.findAll().stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such room"));
    }
}
