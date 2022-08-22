package app.EasyFoodAPI.services;

import app.EasyFoodAPI.models.Account;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.repositories.AccountsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final MapperService mapper;

    public AccountsService(AccountsRepository accountsRepository, MapperService mapper) {
        this.accountsRepository = accountsRepository;
        this.mapper = mapper;
    }

    public Optional<Account> getAccountByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }
}
