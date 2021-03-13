package pl.wsb.chat.domain.exception;

public enum ExceptionCode {
    NO_SUCH_ROOM("C_001", "Brak pokoju o id '%s'.", 404),
    ROOM_ALREADY_EXISTS("C_002", "Pokój o nazwie '%s' już istnieje.", 400),
    ROOM_HAS_BLANK_NAME("C_003", "Nazwa pokoju nie może być pusta.", 400),
    NO_SUCH_USER("C_004", "Brak użytkownika o id '%s'.", 404),
    NO_SUCH_MESSAGE("C_005", "Brak wiadomości o id '%s'.", 404),
    INVALID_MESSAGE("C_006", "Zawartość wiadomości jest niepoprawna.", 400);

    private final String code;
    private final String message;
    private final int status;

    ExceptionCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
