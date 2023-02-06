package com.softserve.itacademy.todolist.config;

import com.softserve.itacademy.todolist.security.JwtTokenFilter;
import com.softserve.itacademy.todolist.security.PersonDetailsService;
import com.softserve.itacademy.todolist.security.WebAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // todo: check https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    private final PersonDetailsService personDetailsService;
    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/users"
    };
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/users/create"
    };

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService,
                          WebAccessDeniedHandler webAccessDeniedHandler,
                          JwtTokenFilter jwtTokenFilter) {
        this.personDetailsService = personDetailsService;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                // spring security will automatically encode password on user login
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .exceptionHandling(eh -> eh
//                        .authenticationEntryPoint(restAuthenticationEntryPoint())
//                )
//                .httpBasic(hb -> hb
//                        .authenticationEntryPoint(restAuthenticationEntryPoint()) // Handles auth error
//                )
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                // rest of urls only for authenticated users and admins
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .headers(h -> h
                        .frameOptions() // for Postman, the H2 console
                        .disable()
                )
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                );

        http.exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler);

        // check every query with jwt filter to get token
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    @Bean
//    AuthenticationEntryPoint restAuthenticationEntryPoint() {
//        return (request, response, authException) -> {
//            log.warn("Authentication for '{} {}' failed with error: {}",
//                    request.getMethod(), request.getRequestURL(), authException.getMessage());
////            response.sendError(
////                    403, authException.getMessage());
//        };
//    }
}
