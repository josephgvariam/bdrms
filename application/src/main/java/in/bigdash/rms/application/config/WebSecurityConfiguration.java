package in.bigdash.rms.application.config;

import in.bigdash.rms.application.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    boolean disableConcurrency = true;

    // Don't make final to allow test cases faking them
    private static String DEFAULT_POLICY_DIRECTIVES = "script-src 'self' 'unsafe-inline' ";

    private static String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";

    private static String LOGIN_FORM_URL = "/login";

    private static String X_CONTENT_SECURITY_POLICY_HEADER = "X-Content-Security-Policy";

    private static String X_WEBKIT_CSP_POLICY_HEADER = "X-WebKit-CSP";

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
    protected void configure(HttpSecurity http) throws Exception {

        // Session management

        if (disableConcurrency) {
            http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired");
        }

        // CSP settings

        http
                .headers()
                .addHeaderWriter(new StaticHeadersWriter(X_CONTENT_SECURITY_POLICY_HEADER,
                        DEFAULT_POLICY_DIRECTIVES))
                .addHeaderWriter(new StaticHeadersWriter(CONTENT_SECURITY_POLICY_HEADER,
                        DEFAULT_POLICY_DIRECTIVES))
                .addHeaderWriter(
                        new StaticHeadersWriter(X_WEBKIT_CSP_POLICY_HEADER, DEFAULT_POLICY_DIRECTIVES));

        // Authentication

        http
                .authorizeRequests()
                .antMatchers("/public/**", "/webjars/**", "/resources/**", "/static/**", "/login/**").permitAll()
                //.antMatchers("/files/**","/requests/**").hasRole("USER")
                //.antMatchers("/users/**", "/roles/**","/clients/**", "/facilities/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_FORM_URL)
                .permitAll()
                .and()
                .logout()
                .permitAll();



    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }
}
