package in.bigdash.rms.application.security;

import in.bigdash.rms.model.User;
import in.bigdash.rms.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        LOG.info("Got user: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new JpaUserDetails(user);
    }
}
