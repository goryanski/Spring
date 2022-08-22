package app.EasyFoodAPI.config;
import app.EasyFoodAPI.services.PersonTestDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // for logic with AuthProviderImpl
//    private final AuthProviderImpl authProvider;
//
//    @Autowired
//    public SecurityConfig(AuthProviderImpl authProvider) {
//        this.authProvider = authProvider;
//    }

    private final PersonTestDetailsService personTestDetailsService;

    public SecurityConfig(PersonTestDetailsService personTestDetailsService) {
        this.personTestDetailsService = personTestDetailsService;
    }

    // configure spring security and authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/auth/login", "/error").permitAll() // urls without authentication
                //.anyRequest().authenticated() // rest of urls only for authenticated users
                .anyRequest().permitAll() // will be deleted
                .and()
                .csrf().disable(); // to allow every HTTP request that modifies state (PATCH, POST, PUT and DELETE — not GET). for JSON data we don't need csrf token, we need it only when we work with forms (in MVC apps for example)
    }

    // for logic with AuthProviderImpl
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authProvider);
//    }






    // configure authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personTestDetailsService);
    }

    // define how the password will be encrypted
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
