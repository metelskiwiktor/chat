package pl.wsb.chat.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void roomControllerBeanShouldBeProvided() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("roomController"));
    }

    @Test
    public void givenReturn() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/message/room/invalidroomid"))
                .andReturn();
        System.out.println(mvcResult);
        Assertions.assertNotNull(mvcResult);
    }
}