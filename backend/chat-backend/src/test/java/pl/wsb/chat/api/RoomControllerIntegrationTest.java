package pl.wsb.chat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.wsb.chat.api.dto.request.AddMessageToRoom;
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomMessage;
import pl.wsb.chat.domain.room.RoomRepository;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIntegrationTest {
    public static final String ENDPOINT_GET_ROOM = "/message/room";
    public static final String ENDPOINT_CREATE_ROOM = "/message/room/create";
    public static final String ENDPOINT_ADD_MESSAGE = "/message/room";
    public static final String ENDPOINT_GET_ROOM_MESSAGES = "/message/room";
    public static final String ENDPOINT_DELETE_ROOM_MESSAGE = "/message/room";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void roomControllerBeanShouldBeProvided() {
        //GIVEN
        //WHEN
        ServletContext servletContext = webApplicationContext.getServletContext();

        //THEN
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("roomController"));
    }

    @Test
    void createdRoomShouldBeInDatabase() throws Exception {
        //GIVEN
        String roomName = "room name";
        int expectedStatus = HttpStatus.CREATED.value();

        //WHEN
        MvcResult mvcResult = createRoomRequest(roomName);
        boolean roomExists = roomRepository.existsByName(roomName);
        boolean responseContainsRoomName = mvcResult.getResponse()
                .getContentAsString()
                .contains("name\":\"" + roomName + "\"");
        int actualStatus = mvcResult.getResponse().getStatus();

        //THEN
        assertTrue(roomExists);
        assertTrue(responseContainsRoomName);
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void createdRoomWithExistingNameShouldReturnBadRequest() throws Exception {
        //GIVEN
        String roomName = "room name";
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        int expectedRoomsWithTheSameNames = 1;

        //WHEN
        createRoomRequest(roomName);
        MvcResult mvcResult = createRoomRequest(roomName);
        int actualStatus = mvcResult.getResponse().getStatus();
        int actualRoomsWithTheSameNames = (int) roomRepository.findAll().stream()
                .filter(room -> room.getName().equals(roomName))
                .count();

        //THEN
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedRoomsWithTheSameNames, actualRoomsWithTheSameNames);
    }

    @Test
    void createdRoomWithBlankNameShouldReturnBadRequest() throws Exception {
        //GIVEN
        String blankRoomName = "";
        int expectedStatus = HttpStatus.BAD_REQUEST.value();

        //WHEN
        MvcResult mvcResult = createRoomRequest(blankRoomName);
        int actualStatus = mvcResult.getResponse().getStatus();
        boolean roomExists = roomRepository.existsByName(blankRoomName);

        //THEN
        assertEquals(expectedStatus, actualStatus);
        assertFalse(roomExists);
    }

    @Test
    void addedMessageToRoomShouldBeInDatabase() throws Exception {
        //GIVEN
        User user = createUser();
        Room room = createRoom();
        AddMessageToRoom addMessageToRoom = new AddMessageToRoom("message note", user.getId());

        //WHEN
        addMessageToRoomRequest(room.getId(), addMessageToRoom);
        boolean roomContainsMessage = roomRepository.findAll().stream()
                .anyMatch(r -> r.getMessages().stream()
                        .anyMatch(rm -> rm.getNote().equals(addMessageToRoom.getNote())));

        //THEN
        assertTrue(roomContainsMessage);
    }

    @Test
    void addedMultipleMessagesToRoomShouldBeInDatabase() throws Exception {
        //GIVEN
        User user = createUser();
        Room room = createRoom();
        AddMessageToRoom addMessageToRoom1 = new AddMessageToRoom("message note 1", user.getId());
        AddMessageToRoom addMessageToRoom2 = new AddMessageToRoom("message note 2", user.getId());
        AddMessageToRoom addMessageToRoom3 = new AddMessageToRoom("message note 3", user.getId());
        int expectedStatus = HttpStatus.OK.value();

        //WHEN
        int actualStatus1 = addMessageToRoomRequest(room.getId(), addMessageToRoom1).getResponse().getStatus();
        int actualStatus2 = addMessageToRoomRequest(room.getId(), addMessageToRoom2).getResponse().getStatus();
        int actualStatus3 = addMessageToRoomRequest(room.getId(), addMessageToRoom3).getResponse().getStatus();
        List<RoomMessage> roomMessages = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages();

        //THEN
        assertEquals(expectedStatus, actualStatus1);
        assertEquals(expectedStatus, actualStatus2);
        assertEquals(expectedStatus, actualStatus3);
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(addMessageToRoom1.getNote())));
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(addMessageToRoom2.getNote())));
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(addMessageToRoom3.getNote())));
    }

    @Test
    void addedMessageWithInvalidRoomShouldReturnNotFound() throws Exception {
        //GIVEN
        User user = createUser();
        String invalidRoomId = "invalid room id";
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        AddMessageToRoom addMessageToRoom = new AddMessageToRoom("message note", user.getId());

        //WHEN
        int actualStatus = addMessageToRoomRequest(invalidRoomId, addMessageToRoom).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void addedInvalidMessageToRoomShouldReturnBadRequest() throws Exception {
        //GIVEN
        User user = createUser();
        Room room = createRoom();
        AddMessageToRoom nullMessage = new AddMessageToRoom(null, user.getId());
        AddMessageToRoom blankMessage = new AddMessageToRoom("", user.getId());
        int expectedStatus = HttpStatus.BAD_REQUEST.value();

        //WHEN
        int actualNullMessageStatus = addMessageToRoomRequest(room.getId(), nullMessage).getResponse().getStatus();
        int actualBlankMessageStatus = addMessageToRoomRequest(room.getId(), blankMessage).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualNullMessageStatus);
        assertEquals(expectedStatus, actualBlankMessageStatus);
    }

    @Test
    void getMessagesShouldReturnListOfMessages() throws Exception {
        //GIVEN
        User user1 = createUser();
        User user2 = createUser();
        Room room1 = createRoom();
        Room room2 = createRoom();
        AddMessageToRoom addMessageToRoom1 = new AddMessageToRoom("room note 1", user1.getId());
        AddMessageToRoom addMessageToRoom2 = new AddMessageToRoom("room note 2", user2.getId());
        AddMessageToRoom addMessageToRoom3 = new AddMessageToRoom("room note 3", user2.getId());
        int expectedStatus = HttpStatus.OK.value();

        //WHEN
        addMessageToRoomRequest(room1.getId(), addMessageToRoom1);
        addMessageToRoomRequest(room2.getId(), addMessageToRoom2);
        addMessageToRoomRequest(room1.getId(), addMessageToRoom3);
        MvcResult mvcResult = getMessagesFromRoomRequest(room1.getId());
        int actualStatus = mvcResult.getResponse().getStatus();
        boolean containsMessage1 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom1.getNote() + "\"");
        boolean containsMessage2 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom2.getNote() + "\"");
        boolean containsMessage3 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom3.getNote() + "\"");

        //THEN
        assertEquals(expectedStatus, actualStatus);
        assertTrue(containsMessage1);
        assertFalse(containsMessage2);
        assertTrue(containsMessage3);
    }

    @Test
    void getMessagesByInvalidIdShouldReturnNotFound() throws Exception {
        //GIVEN
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        String notExistingRoomId = "not existing room id";

        //WHEN
        int actualStatus = getRoomRequest(notExistingRoomId).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteMessageRoomRequestShouldDeleteMessageRoomFromDatabase() throws Exception {
        //GIVEN
        Room room = createRoom();
        User user = createUser();
        int expectedStatus = HttpStatus.OK.value();
        AddMessageToRoom addMessageToRoom = new AddMessageToRoom("message note 1", user.getId());
        addMessageToRoomRequest(room.getId(), addMessageToRoom);
        String messageId = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages().stream()
                .filter(rm -> rm.getNote().equals(addMessageToRoom.getNote()))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getId();

        //WHEN
        int actualStatus = deleteMessageRoomRequest(messageId).getResponse().getStatus();
        boolean existMessage = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages().stream()
                .anyMatch(rm -> rm.getNote().equals(addMessageToRoom.getNote()));

        //THEN
        assertEquals(expectedStatus, actualStatus);
        assertFalse(existMessage);
    }

    @Test
    void deleteMessageWithInvalidMessageIdShouldReturnNotFound() throws Exception {
        //GIVEN
        String invalidMessageId = "invalid message id";
        int expectedStatus = HttpStatus.NOT_FOUND.value();

        //WHEN
        int actualStatus = deleteMessageRoomRequest(invalidMessageId).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualStatus);
    }

    private User createUser() {
        return userRepository.save(new User("user"));
    }

    private Room createRoom() {
        return roomRepository.save(new Room("room name", new ArrayList<>()));
    }

    private MvcResult getRoomRequest(String roomId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_ROOM + "/" + roomId))
                .andReturn();
    }

    private MvcResult createRoomRequest(String roomName) throws Exception {
        return mockMvc.perform
                (MockMvcRequestBuilders
                        .post(ENDPOINT_CREATE_ROOM)
                        .content(roomName))
                .andReturn();
    }

    private MvcResult addMessageToRoomRequest(String roomId, AddMessageToRoom addMessageToRoom) throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(addMessageToRoom);

        return mockMvc.perform
                (MockMvcRequestBuilders
                        .post(ENDPOINT_ADD_MESSAGE + "/" + roomId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andReturn();
    }

    private MvcResult getMessagesFromRoomRequest(String roomId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_ROOM_MESSAGES + "/" + roomId))
                .andReturn();
    }

    private MvcResult deleteMessageRoomRequest(String messageId) throws Exception {
        return mockMvc.perform
                (MockMvcRequestBuilders
                        .delete(ENDPOINT_DELETE_ROOM_MESSAGE)
                        .param("message-id", messageId))
                .andReturn();
    }
}