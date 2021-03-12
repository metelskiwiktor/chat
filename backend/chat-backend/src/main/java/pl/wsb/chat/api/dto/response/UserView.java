package pl.wsb.chat.api.dto.response;

public class UserView {
    private String id;
    private String login;

    public UserView() {
    }

    public UserView(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
