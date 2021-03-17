package pl.wsb.chat.lib;

import pl.wsb.chat.domain.exception.DomainException;

import java.util.Objects;
import java.util.function.Supplier;

import static org.apache.logging.log4j.util.Strings.isBlank;

public final class Assertion {
    private Assertion() {
    }

    public static void isBiggerEqualsThan(int requiredMinimumSize, String value, Supplier<DomainException> exception) {
        notEmpty(value, exception);
        isTrue(value.length() >= requiredMinimumSize, exception);
    }

    public static void isTrue(boolean value, Supplier<DomainException> exception) {
        if (!value) {
            throw exception.get();
        }
    }

    public static void notEmpty(String value, Supplier<DomainException> exception) {
        notNull(value, exception);
        if (isBlank(value)) {
            throw exception.get();
        }
    }

    public static void notNull(Object object, Supplier<DomainException> exception) {
        if (Objects.isNull(object)) {
            throw exception.get();
        }
    }
}
