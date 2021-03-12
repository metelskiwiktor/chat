package pl.wsb.chat.infrastructure;

import org.bson.types.ObjectId;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.wsb.chat.domain.pm.PrivateMessageRepository;
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomMessage;
import pl.wsb.chat.domain.room.RoomRepository;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Profile("init")
@Component
public class SampleDataInitialization implements ApplicationRunner {
    private final PrivateMessageRepository privateMessageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public SampleDataInitialization(PrivateMessageRepository privateMessageRepository,
                                    RoomRepository roomRepository,
                                    UserRepository userRepository) {
        this.privateMessageRepository = privateMessageRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    private void saveSampleDataIfRepoEmpty() {
        System.out.println("==============================\nSTARTING INITIALIZATION");
        int pmSize = privateMessageRepository.findAll().size();
        int rSize = roomRepository.findAll().size();
        int uSize = userRepository.findAll().size();

        if (pmSize > 0 || rSize > 0 || uSize > 0) return;

        User wihajster = new User("wihajster123");
        User ziomek = new User("ziomek");

        userRepository.save(wihajster);
        userRepository.save(ziomek);

        RoomMessage testNote1 = new RoomMessage(
                new ObjectId().toString(),
                "testnote1",
                LocalDateTime.now(),
                wihajster
        );

        RoomMessage testNote2 = new RoomMessage(
                new ObjectId().toString(),
                "testnote2",
                LocalDateTime.now(),
                ziomek
        );

        Room room = new Room(
                "#3403",
                Arrays.asList(testNote1, testNote2)
        );

        roomRepository.save(room);

        roomRepository.findAll()
                .forEach(System.out::println);

        System.out.println("END OF INITIALIZATION\n==============================");
    }

    @Override
    public void run(ApplicationArguments args) {
        saveSampleDataIfRepoEmpty();
    }
}
