package app.EasyFoodAPI.security;

import app.EasyFoodAPI.models.PersonTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// class for spring security - class-wrapper for Person entity. use methods from UserDetails
public class PersonDetails implements UserDetails {
    // later it'll be account object
    private final PersonTest person;

    public PersonDetails(PersonTest person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // do later
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // password is non expired
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // we need it to get data of authenticated user
    // return person info object. later it will be account.getPerson()
    public PersonTest getPerson() {
        return this.person;
    }
}
