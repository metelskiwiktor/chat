package pl.wsb.chat.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.wsb.chat.domain.user.Role;

@Configuration
@EnableWebSecurity()
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationSuccessHandler authenticationSuccessHandler;
    private final RestAuthenticationFailureHandler authenticationFailureHandler;
    private final UserDetailsServiceDatabase userDetailsServiceDatabase;
    private final String secret;

    public SecurityConfig(RestAuthenticationSuccessHandler authenticationSuccessHandler,
                          RestAuthenticationFailureHandler authenticationFailureHandler,
                          UserDetailsServiceDatabase userDetailsServiceDatabase,
                          @Value("${jwt.secret}") String secret) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.userDetailsServiceDatabase = userDetailsServiceDatabase;
        this.secret = secret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthorizationFilter authenticationTokenFilter =
                new JwtAuthorizationFilter(authenticationManager(), userDetailsServiceDatabase, secret);

        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/mock", "/message/room/*").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/message/*").hasRole(Role.MODERATOR.name())
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CorsFilterConfig(), ChannelProcessingFilter.class)
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(authenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }
}
