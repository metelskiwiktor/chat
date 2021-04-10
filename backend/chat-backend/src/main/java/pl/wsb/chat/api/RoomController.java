package pl.wsb.chat.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.api.dto.response.RoomMessageView;
import pl.wsb.chat.api.dto.response.RoomView;
import pl.wsb.chat.domain.room.RoomService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/message/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomView> createRoom(@RequestBody String roomName) {
        return new ResponseEntity<>(roomService.createRoom(roomName), HttpStatus.CREATED);
    }

    @PostMapping("/{room-id}")
    public void addMessage(@PathVariable("room-id") String roomId, @RequestBody String message) {
        roomService.addMessage(roomId, message);
    }

    @GetMapping("/{room-id}")
    public List<RoomMessageView> getMessages(@PathVariable("room-id") String roomId) {
        return roomService.getMessages(roomId);
    }

    @GetMapping("/all-rooms")
    public List<RoomView> getRooms() {
        return roomService.getRooms();
    }

    @DeleteMapping
    public void deleteMessage(@RequestParam("message-id") String messageId) {
        roomService.deleteMessage(messageId);
    }
}
