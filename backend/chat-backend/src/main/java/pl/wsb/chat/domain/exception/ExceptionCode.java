package pl.wsb.chat.domain.exception;

public enum  ExceptionCode {
    NO_SUCH_ROOM("C_001", "Brak pokoju o id '%s'.", 404),
    ROOM_ALREADY_EXIST("C_002", "Pokój o nazwie '%s' już istnieje.", 404),
    NO_SUCH_USER("C_003", "Brak użytkownika od id '%s'.", 404);

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
