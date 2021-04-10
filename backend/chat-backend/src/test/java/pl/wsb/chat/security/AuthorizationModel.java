package pl.wsb.chat.security;

import pl.wsb.chat.domain.user.User;

public class AuthorizationModel {
    private final String jwt;
    private final User user;

    public AuthorizationModel(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser() {
        return user;
    }
}
