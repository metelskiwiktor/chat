package pl.wsb.chat.infrastructure.local.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomRepository;

public interface RoomRepositoryLocal extends RoomRepository, MongoRepository<Room, String> {

}
