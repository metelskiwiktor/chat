package pl.wsb.chat.domain.room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAll();

    Room save(Room room);

    boolean existsByName(String name);

    Optional<Room> findById(String roomId);

    void deleteAll();
}
