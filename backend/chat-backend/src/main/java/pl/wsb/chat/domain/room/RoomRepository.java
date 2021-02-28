package pl.wsb.chat.domain.room;

import java.util.List;

public interface RoomRepository {
    List<Room> findAll();
    Room save(Room room);
}
