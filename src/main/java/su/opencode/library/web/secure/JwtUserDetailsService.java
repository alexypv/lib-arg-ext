package su.opencode.library.web.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.service.user.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.getUserByUsername(username) != null) {
            UserEntity userEntity = userService.getUserByUsername(username);
            return JwtUserFactory.create(userEntity);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}