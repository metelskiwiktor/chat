package pl.wsb.chat.api;

import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomMessage;
import pl.wsb.chat.domain.room.RoomService;

import java.util.List;

@RestController
@RequestMapping("message/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName){
        return roomService.createRoom(roomName);
    }

    @PostMapping("/{roomId}")
    public void addMessage(@PathVariable String roomId, @RequestBody RoomMessage roomMessage){
        roomService.addMessage(roomId, roomMessage);
    }

    @GetMapping("/{roomId}")
    public List<RoomMessage> getMessages(@PathVariable String roomId){
        return roomService.getMessages(roomId);
    }

    @DeleteMapping("/{roomId}")
    public void deleteMessage(@PathVariable String roomId, @RequestParam String messageId){
        roomService.deleteMessage(roomId, messageId);
    }
}
