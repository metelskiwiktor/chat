package pl.wsb.chat.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ProjectConfiguration {
    @Bean
    public Clock defaultClock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withLocale(new Locale("pl"))
                .withZone(ZoneId.systemDefault());
    }
}
