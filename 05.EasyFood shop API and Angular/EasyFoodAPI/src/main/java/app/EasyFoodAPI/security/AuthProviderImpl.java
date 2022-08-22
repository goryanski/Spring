//package app.EasyFoodAPI.security;
//
//import app.EasyFoodAPI.services.PersonTestDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//// Удаляем этот класс
//
//@Component
//public class AuthProviderImpl implements AuthenticationProvider {
//    private final PersonTestDetailsService personTestDetailsService;
//
//    @Autowired
//    public AuthProviderImpl(PersonTestDetailsService personTestDetailsService) {
//        this.personTestDetailsService = personTestDetailsService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        // get user from db
//        UserDetails personDetails = personTestDetailsService.loadUserByUsername(username);
//        String password = authentication.getCredentials().toString();
//        // compare password we get from user with password in db
//        if(!password.equals(personDetails.getPassword())) {
//            throw new BadCredentialsException("Incorrect password");
//        }
//
//        // if username and password correct - return principle (user info object)
//        // third argument - list of authorities of person for authorization (список прав пользователя)
//        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true; // we have only one Authentication Provider, so here always be true
//    }
//}
