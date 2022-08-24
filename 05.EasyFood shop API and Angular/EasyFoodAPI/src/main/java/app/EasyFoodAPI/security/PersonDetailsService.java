package app.EasyFoodAPI.security;

import app.EasyFoodAPI.models.Account;
import app.EasyFoodAPI.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

// this service for spring security
@Service
public class PersonDetailsService implements UserDetailsService {
    private final AccountsService accountsService;

    @Autowired
    public PersonDetailsService(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountsService.getAccountByUsername(username);
        if(account.isEmpty()) {
            // will be caught by spring security
            throw new UsernameNotFoundException("User not found");
        }
        return new PersonDetails(account.get());
    }
}
