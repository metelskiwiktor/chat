package pl.wsb.chat.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.bytebuddy.utility.RandomString;
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
import java.util.List;

@Component
public class UserPrincipalAuth {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthorizationModel getAuth(Role... roles) {
        return createJWT(Arrays.asList(roles));
    }

    public AuthorizationModel getAuth() {
        return getAuth(Role.STANDARD);
    }

    private AuthorizationModel createJWT(List<Role> roles) {
        try {
            String randomLogin = new RandomString().nextString();
            String randomPassword = new RandomString().nextString();
            User user = userRepository.save(new User(
                    randomLogin,
                    new BCryptPasswordEncoder().encode(randomPassword),
                    new HashSet<>(roles)
            ));

            ObjectWriter ow = mapper.writer();
            String requestJson = ow.writeValueAsString(new LoginCredentials(randomLogin, randomPassword));

            MvcResult mvcResult = mockMvc.perform
                    (MockMvcRequestBuilders
                            .post("/login")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andReturn();
            String jwt = "Bearer " + mvcResult.getResponse().getHeader("Authorization");

            return new AuthorizationModel(jwt, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Critical error during login request attempt");
        }
    }
}
