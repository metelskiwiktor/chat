package pl.wsb.chat.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int expirationTime;
    private final String secret;
    private final PrincipalInformation principalInformation;

    public RestAuthenticationSuccessHandler(@Value("${jwt.expirationTime}") int expirationTime,
                                            @Value("${jwt.secret}") String secret,
                                            PrincipalInformation principalInformation) {
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.principalInformation = principalInformation;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim("roles", getClaims(principal.getAuthorities()))
                .withClaim("userId", principalInformation.getUserId(principal.getUsername()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        response.addHeader("Authorization", token);
        response.getOutputStream().print("{\"token\": \"" + token + "\"}");
    }

    private String[] getClaims(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .toArray(new String[authorities.size()]);
    }
}
