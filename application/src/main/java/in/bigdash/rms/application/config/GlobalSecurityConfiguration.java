package in.bigdash.rms.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class GlobalSecurityConfiguration  extends GlobalAuthenticationConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * {@inheritDoc}
     *
     * Initializes the default {@link UserDetailsService} causing the {@link AuthenticationManagerBuilder}
     * creates automatically the {@link DaoAuthenticationProvider} that delegates on the given
     * {@link UserDetailsService}.
     *
     * Also setup the {@link BCryptPasswordEncoder} to use with the {@link DaoAuthenticationProvider}
     */
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
