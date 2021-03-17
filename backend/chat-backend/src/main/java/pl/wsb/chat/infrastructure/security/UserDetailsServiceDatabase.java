package pl.wsb.chat.infrastructure.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wsb.chat.domain.user.Role;
import pl.wsb.chat.domain.user.User;
import pl.wsb.chat.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Service
@Order(1)
public class UserDetailsServiceDatabase implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceDatabase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with login " + login));

        return new UserPrincipal(user);
    }

    private static class UserPrincipal implements UserDetails {
        private final User user;

        public UserPrincipal(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if (Objects.isNull(user.getRoles())) {
                return new ArrayList<>();
            }

            HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>(user.getRoles().size());

            for (Role role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return grantedAuthorities;
        }

        @Override
        public String getPassword() {
            return "{bcrypt}" + user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getLogin();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
