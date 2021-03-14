package pl.wsb.chat.domain.pm;

import java.util.List;

public interface PrivateMessageRepository {
    List<PrivateMessage> findAll();

    PrivateMessage save(PrivateMessage privateMessage);
}
