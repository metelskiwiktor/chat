package pl.wsb.chat.infrastructure.security;

public class LoginCredentials {
    private String login;
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String login, String password) {
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
