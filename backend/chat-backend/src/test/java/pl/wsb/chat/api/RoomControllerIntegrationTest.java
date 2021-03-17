package pl.wsb.chat.api;

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
import pl.wsb.chat.domain.room.Room;
import pl.wsb.chat.domain.room.RoomMessage;
import pl.wsb.chat.domain.room.RoomRepository;
import pl.wsb.chat.domain.user.UserRepository;
import pl.wsb.chat.security.UserPrincipalAuth;

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
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPrincipalAuth userPrincipalAuth;

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
        Room room = createRoom();
        String message = "message note";

        //WHEN
        addMessageToRoomRequest(room.getId(), message);
        boolean roomContainsMessage = roomRepository.findAll().stream()
                .anyMatch(r -> r.getMessages().stream()
                        .anyMatch(rm -> rm.getNote().equals(message)));

        //THEN
        assertTrue(roomContainsMessage);
    }

    @Test
    void addedMultipleMessagesToRoomShouldBeInDatabase() throws Exception {
        //GIVEN
        Room room = createRoom();
        String message1 = "message note 1";
        String message2 = "message note 2";
        String message3 = "message note 3";
        int expectedStatus = HttpStatus.OK.value();

        //WHEN
        int actualStatus1 = addMessageToRoomRequest(room.getId(), message1).getResponse().getStatus();
        int actualStatus2 = addMessageToRoomRequest(room.getId(), message2).getResponse().getStatus();
        int actualStatus3 = addMessageToRoomRequest(room.getId(), message3).getResponse().getStatus();
        List<RoomMessage> roomMessages = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages();

        //THEN
        assertEquals(expectedStatus, actualStatus1);
        assertEquals(expectedStatus, actualStatus2);
        assertEquals(expectedStatus, actualStatus3);
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(message1)));
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(message2)));
        assertTrue(roomMessages.stream().anyMatch(rm -> rm.getNote().equals(message3)));
    }

    @Test
    void addedMessageWithInvalidRoomShouldReturnNotFound() throws Exception {
        //GIVEN
        String invalidRoomId = "invalid room id";
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        String message = "message note";

        //WHEN
        int actualStatus = addMessageToRoomRequest(invalidRoomId, message).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void addedEmptyMessageToRoomShouldReturnBadRequest() throws Exception {
        //GIVEN
        Room room = createRoom();
        String blankMessage = "";
        int expectedStatus = HttpStatus.BAD_REQUEST.value();

        //WHEN
        int actualBlankMessageStatus = addMessageToRoomRequest(room.getId(), blankMessage).getResponse().getStatus();

        //THEN
        assertEquals(expectedStatus, actualBlankMessageStatus);
    }

    @Test
    void getMessagesShouldReturnListOfMessages() throws Exception {
        //GIVEN
        Room room1 = createRoom();
        Room room2 = createRoom();
        String addMessageToRoom1 = "room note 1";
        String addMessageToRoom2 = "room note 2";
        String addMessageToRoom3 = "room note 3";
        int expectedStatus = HttpStatus.OK.value();

        //WHEN
        addMessageToRoomRequest(room1.getId(), addMessageToRoom1);
        addMessageToRoomRequest(room2.getId(), addMessageToRoom2);
        addMessageToRoomRequest(room1.getId(), addMessageToRoom3);
        MvcResult mvcResult = getMessagesFromRoomRequest(room1.getId());
        int actualStatus = mvcResult.getResponse().getStatus();
        boolean containsMessage1 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom1 + "\"");
        boolean containsMessage2 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom2 + "\"");
        boolean containsMessage3 = mvcResult.getResponse()
                .getContentAsString()
                .contains("note\":\"" + addMessageToRoom3 + "\"");

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
        int expectedStatus = HttpStatus.OK.value();
        String message = "message note 1";
        addMessageToRoomRequest(room.getId(), message);
        String messageId = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages().stream()
                .filter(rm -> rm.getNote().equals(message))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getId();

        //WHEN
        int actualStatus = deleteMessageRoomRequest(messageId).getResponse().getStatus();
        boolean existMessage = roomRepository.findById(room.getId())
                .orElseThrow(RuntimeException::new)
                .getMessages().stream()
                .anyMatch(rm -> rm.getNote().equals(message));

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
                        .header("Authorization", userPrincipalAuth.getJWT())
                        .content(roomName))
                .andReturn();
    }

    private MvcResult addMessageToRoomRequest(String roomId, String message) throws Exception {
        return mockMvc.perform
                (MockMvcRequestBuilders
                        .post(ENDPOINT_ADD_MESSAGE + "/" + roomId)
                        .header("Authorization", userPrincipalAuth.getJWT())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(message))
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
                        .header("Authorization", userPrincipalAuth.getJWT())
                        .param("message-id", messageId))
                .andReturn();
    }
}
