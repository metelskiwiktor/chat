package pl.wsb.chat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wsb.chat.domain.exception.DomainException;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class CustomExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DateTimeFormatter formatter;
    private final Clock clock;

    public CustomExceptionHandler(DateTimeFormatter formatter, Clock clock) {
        this.formatter = formatter;
        this.clock = clock;
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Error> handleDomainException(DomainException exception) {
        logger.error("Domain exception occurred");
        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getCode().getStatus());
        Error body = new Error(httpStatus.value(), exception.getMessage(), formatter.format(clock.instant()));

        return new ResponseEntity<>(body, httpStatus);
    }

    private static class Error {
        private final int status;
        private final String message;
        private final String timestamp;

        private Error(int status, String message, String timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }

    }
}
