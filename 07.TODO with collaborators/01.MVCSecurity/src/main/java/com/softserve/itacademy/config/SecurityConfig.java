package com.softserve.itacademy.config;
import com.softserve.itacademy.security.PersonDetailsService;
import com.softserve.itacademy.security.WebAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final WebAccessDeniedHandler webAccessDeniedHandler;

    private static final String[] ADMIN_ENDPOINTS = {
            "/",
            "/home",
    };
    private static final String[] PUBLIC_ENDPOINTS = {
            "/login",
            "/users/create"
    };

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, WebAccessDeniedHandler webAccessDeniedHandler) {
        this.personDetailsService = personDetailsService;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                        .authorizeRequests()
                        .antMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                        // rest of urls only for authenticated users and admins
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                        .and()
                        .formLogin().loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/get_home_page", true)
                        .failureUrl("/login?error")
                        .and()
                        .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        http.exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                // spring security will automatically encode password on user login
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
