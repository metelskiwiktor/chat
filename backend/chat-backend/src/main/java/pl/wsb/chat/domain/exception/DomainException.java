package pl.wsb.chat.domain.exception;

public class DomainException extends RuntimeException {
    private final ExceptionCode code;

    public DomainException(ExceptionCode code, Object... arguments) {
        super(String.format(code.getMessage(), arguments));
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
