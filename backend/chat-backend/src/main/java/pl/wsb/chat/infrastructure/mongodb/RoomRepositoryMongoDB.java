package pl.wsb.chat.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomRepository;

public interface RoomRepositoryMongoDB extends RoomRepository, MongoRepository<Room, String> {
    boolean existsByName(String name);
}
