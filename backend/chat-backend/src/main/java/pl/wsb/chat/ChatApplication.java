package pl.wsb.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {
    static {
        System.out.println("DZIAŁA STATIC");
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
