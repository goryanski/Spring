package app.EasyFoodAPI.config;
import app.EasyFoodAPI.security.JwtTokenFilter;
import app.EasyFoodAPI.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String ADMIN_ENDPOINT = "/api/easyFood/admin/**";
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/easyFood/auth/**",
            "/api/easyFood/home",
            "/api/easyFood/products/**",
            "/api/easyFood/products-filter/**"
    };


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    // configure spring security and authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // to allow every HTTP request that modifies state (PATCH, POST, PUT and DELETE — not GET). for JSON data we don't need csrf token, we need it only when we work with forms (in MVC apps for example)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                // rest of urls only for authenticated users and admins
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                // do not create sessions for users (because we use jwt)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // check every query with jwt filter to get token
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    // configure authentication (encrypt password before check user during authentication)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder()); // been PasswordEncoder below
    }

    // define how the password will be encrypted
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
