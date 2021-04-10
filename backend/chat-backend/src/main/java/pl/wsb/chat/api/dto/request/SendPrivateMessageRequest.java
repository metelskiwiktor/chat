package pl.wsb.chat.api.dto.request;

public class SendPrivateMessageRequest {
    private String message;
    private String recipientId;

    public SendPrivateMessageRequest() {
    }

    public SendPrivateMessageRequest(String message, String recipientId) {
        this.message = message;
        this.recipientId = recipientId;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipientId() {
        return recipientId;
    }
}
