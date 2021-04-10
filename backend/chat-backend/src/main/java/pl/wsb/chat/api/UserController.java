package pl.wsb.chat.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.chat.api.dto.request.RegisterUserRequest;
import pl.wsb.chat.api.dto.response.UserProfileView;
import pl.wsb.chat.domain.user.Role;
import pl.wsb.chat.domain.user.UserService;

import java.util.Collections;
import java.util.HashSet;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.register(registerUserRequest, Collections.singleton(Role.STANDARD));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public UserProfileView getUserProfileView(@PathVariable String username){
        return userService.getUserProfile(username);
    }
}
