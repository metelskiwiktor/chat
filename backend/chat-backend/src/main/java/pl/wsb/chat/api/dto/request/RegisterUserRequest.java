package pl.wsb.chat.api.dto.request;

public class RegisterUserRequest {
    private String login;
    private String password;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
