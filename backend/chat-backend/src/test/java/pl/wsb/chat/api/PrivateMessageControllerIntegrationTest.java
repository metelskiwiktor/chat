package pl.wsb.chat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.wsb.chat.api.dto.request.SendPrivateMessageRequest;
import pl.wsb.chat.domain.pm.PrivateMessageRepository;
import pl.wsb.chat.domain.pm.PrivateMessageService;
import pl.wsb.chat.security.UserPrincipalAuth;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PrivateMessageControllerIntegrationTest {
    public static final String ENDPOINT_SEND_PRIVATE_MESSAGE = "/message/private";
    public static final String ENDPOINT_GET_CONVERSATION = "/message/private/conversation";
    public static final String ENDPOINT_GET_CONVERSATIONS = "/message/private/conversations";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserPrincipalAuth userPrincipalAuth;
    @Autowired
    private PrivateMessageService privateMessageService;
    @Autowired
    private PrivateMessageRepository privateMessageRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    //wyslij poprawną wiadomosc do poprawnego odbiorcy
    public void privateMessage_validMessage_sent() throws Exception {

    }

    //wyslij niepoprawną wiadomosc do poprawnego odbiorcy
    public void privateMessage_blankMessage_notSent() throws Exception {

    }

    //wyslij poprawną wiadomość do niepoprawnego odbiorcy
    public void privateMessage_validMessageToInvalidRecipient_notSent() throws Exception {

    }

    //zwroc wszystkie wiadomosci o podanym prawidlowym id odbiorcy
    public void privateMessage_getMessagesFromRecipient_areShown() throws Exception {

    }

    //zwroc wszystkie wiadomosci o podanym nieprawidlowym id odbiorcy
    public void privateMessage_getMessagesFromInvalidRecipient_exceptionThrown() throws Exception {

    }

    //zwroc wszystkie konwersacje przy wielu konwersacjach
    public void privateMessage_getConversations_returnManyConversations() throws Exception {

    }

    //zwroc wszystkie konwersacje przy zadnej konwersacji
    public void privateMessage_getConversationsWithNoData_returnEmptyList() throws Exception {

    }

    private MvcResult sendPrivateMessageRequest(SendPrivateMessageRequest sendPrivateMessageRequest) throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(sendPrivateMessageRequest);

        return mockMvc.perform
                (MockMvcRequestBuilders
                        .post(ENDPOINT_SEND_PRIVATE_MESSAGE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .header("Authorization", userPrincipalAuth.getAuth().getJwt()))
                .andReturn();
    }

    private MvcResult getPrivateMessagesRequest(String recipientId) throws Exception {
        return mockMvc.perform
                (MockMvcRequestBuilders
                        .get(ENDPOINT_GET_CONVERSATION)
                        .param("recipient-id", recipientId)
                        .header("Authorization", userPrincipalAuth.getAuth().getJwt()))
                .andReturn();
    }

    private MvcResult getConversationsRequest() throws Exception {
        return mockMvc.perform
                (MockMvcRequestBuilders
                        .get(ENDPOINT_GET_CONVERSATIONS)
                        .header("Authorization", userPrincipalAuth.getAuth().getJwt()))
                .andReturn();
    }
}