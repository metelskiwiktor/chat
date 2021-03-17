package pl.wsb.chat.domain.exception;

public enum ExceptionCode {
    NO_SUCH_ROOM("C_001", "Brak pokoju o id '%s'.", 404),
    ROOM_ALREADY_EXISTS("C_002", "Pokój o nazwie '%s' już istnieje.", 400),
    NO_SUCH_USER("C_004", "Brak użytkownika o id '%s'.", 404),
    NO_SUCH_MESSAGE("C_005", "Brak wiadomości o id '%s'.", 404),
    USER_ALREADY_EXISTS("C_006", "Nazwa użytkownika '%s' jest zajęta.", 400),
    FIELD_IS_TOO_SHORT("C_007", "Zawartość pola '%s' jest za krótka.", 400);

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
