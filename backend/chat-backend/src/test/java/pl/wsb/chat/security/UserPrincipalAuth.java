package pl.wsb.chat.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.wsb.chat.domain.user.Role;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;
import pl.wsb.chat.infrastructure.security.LoginCredentials;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class UserPrincipalAuth {
    public static final String login = "testlogin";
    public static final String password = "password";
    private String jwt;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private User user;

    public String getJWT() {
        createIfUserNotExists();
        return jwt;
    }

    public User getUser() {
        createIfUserNotExists();
        return user;
    }

    private void createIfUserNotExists() {
        if (!userRepository.existsByLogin(login)) {
            createJWT();
        }
    }

    private void createJWT() {
        try {
            user = userRepository.save(new User(
                    login,
                    new BCryptPasswordEncoder().encode(password),
                    new HashSet<>(Arrays.asList(Role.STANDARD, Role.MODERATOR))
            ));

            ObjectWriter ow = mapper.writer();
            String requestJson = ow.writeValueAsString(new LoginCredentials(login, password));

            MvcResult mvcResult = mockMvc.perform
                    (MockMvcRequestBuilders
                            .post("/login")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andReturn();
            jwt = "Bearer " + mvcResult.getResponse().getHeader("Authorization");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
