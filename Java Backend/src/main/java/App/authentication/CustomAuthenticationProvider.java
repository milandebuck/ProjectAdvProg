package App.authentication;

import App.authentication.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *   We have to implement our own custom authenticationprovider so that spring security routes to this one instead.
 **/
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    public CustomAuthenticationProvider() {
    }

    /**
     *   This method checks to see if the usercredentials are valid
     *   If not throw BadCredentialsException
     *   If yes return UsernamePasswordAuthenticationToken
     **/
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        User user = this.userService.loadUserByUsername(username);
        if(user != null && this.encoder.matches(password, user.getPassword())) {
            Collection authorities = user.getAuthorities();
            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        } else {
            throw new BadCredentialsException("Bad username or password!");
        }
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
}