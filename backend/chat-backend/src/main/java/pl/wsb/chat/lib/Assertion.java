package pl.wsb.chat.lib;

import org.apache.commons.lang3.StringUtils;
import pl.wsb.chat.domain.exception.DomainException;

import java.util.Objects;
import java.util.function.Supplier;

public final class Assertion {
    private Assertion() {
    }

    public static void notEmpty(String value, Supplier<DomainException> exception) {
        notNull(value, exception);
        if (StringUtils.isBlank(value)) {
            throw exception.get();
        }
    }

    public static void notNull(Object object, Supplier<DomainException> exception) {
        if (Objects.isNull(object)) {
            throw exception.get();
        }
    }
}
