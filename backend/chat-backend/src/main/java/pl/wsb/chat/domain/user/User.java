package pl.wsb.chat.domain.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private Set<Role> roles;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(String login) {
        this.login = login;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", roles=" + roles +
                '}';
    }
}
