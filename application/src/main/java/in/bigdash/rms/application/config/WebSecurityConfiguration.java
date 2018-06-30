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
import org.springframework.web.accept.ContentNegotiationStrategy;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    boolean disableConcurrency = true;

    // Don't make final to allow test cases faking them
    private static String DEFAULT_POLICY_DIRECTIVES = "script-src 'self' 'unsafe-inline' 'unsafe-eval'";

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
                .addHeaderWriter(new StaticHeadersWriter(X_CONTENT_SECURITY_POLICY_HEADER, DEFAULT_POLICY_DIRECTIVES))
                .addHeaderWriter(new StaticHeadersWriter(CONTENT_SECURITY_POLICY_HEADER, DEFAULT_POLICY_DIRECTIVES))
                .addHeaderWriter(new StaticHeadersWriter(X_WEBKIT_CSP_POLICY_HEADER, DEFAULT_POLICY_DIRECTIVES));

        // Authentication

        http
                .authorizeRequests()
                .antMatchers("/public/**", "/webjars/**", "/resources/**", "/static/**", "/login/**").permitAll()
                .antMatchers("/**/workflow/**").hasRole("OPERATOR")
                .antMatchers("/requests/**/edit-form").hasRole("OPERATOR")

                .antMatchers("/inventoryitems/**","/requests/**").hasRole("USER")
                .antMatchers("/boxes/**", "/files/**","/documents/**", "/shelves/**", "/users/s2o").hasRole("OPERATOR")
                .antMatchers("/users/**", "/roles/**","/clients/**", "/facilities/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(LOGIN_FORM_URL).permitAll()
                .and()
                .logout().permitAll();

        http.csrf().ignoringAntMatchers("/api/**");

    }

    /**
     * By default, this method provided by {@link WebSecurityConfigurerAdapter}
     * is annotated with @Autowired. That causes that the {@link ContentNegotiationStrategy}
     * load process registers a ConversionService before the Formatters have been registered
     * in the Spring Context.
     *
     * To solve this, override the default method removing the @Autowired annotation
     * to be able to load the Formatters before the Conversion Service register
     * them.
     *
     * https://stackoverflow.com/questions/42698006/select2-autocomplete-error-on-views-spring-roo-rc1
     *
     */
    @Override
  	public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
    	super.setContentNegotationStrategy(contentNegotiationStrategy);
  	}

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }
}
