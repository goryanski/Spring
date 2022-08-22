package app.EasyFoodAPI.services;

import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.models.Account;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.repositories.AccountsRepository;
import app.EasyFoodAPI.repositories.PeopleRepository;
import app.EasyFoodAPI.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final PeopleRepository peopleRepository;
    private final AccountsRepository accountsRepository;
    private final RolesRepository rolesRepository;
    private final MapperService mapper;

    @Autowired
    public AuthService(PeopleRepository peopleRepository, AccountsRepository accountsRepository, RolesRepository rolesRepository, MapperService mapper) {
        this.peopleRepository = peopleRepository;
        this.accountsRepository = accountsRepository;
        this.rolesRepository = rolesRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void registerPerson(RegisterPersonDTO registerPerson) {
        Person person = fillPerson(registerPerson);
        Person savedPerson = peopleRepository.save(person);

        Account account = fillAccount(registerPerson);
        account.setPerson(savedPerson);
        accountsRepository.save(account);
    }


    private Person fillPerson(RegisterPersonDTO registerPerson) {
        // map manually because when modelMapper maps date string to Date object - there is an exception, and we also need some fields set without modelMapper
        Person person = new Person();
        person.setFullName(registerPerson.getFullName());
        person.setEmail(registerPerson.getEmail());
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(registerPerson.getDateOfBirth());
            person.setDateOfBirth(date);
        } catch (Exception e) {
            // nothing to catch because we absolutely sure that date is valid - we check it in PersonValidator already, but we still need wrap it with try catch - parse() throws exception
        }
        person.setBlocked(false);
        person.setRegisteredAt(LocalDateTime.now());
        return person;
    }

    private Account fillAccount(RegisterPersonDTO registerPerson) {
        Account account = new Account();
        account.setUsername(registerPerson.getUsername());
        account.setPassword(registerPerson.getPassword());
        account.setRole(rolesRepository.findById(1)); // user role always
        return account;
    }
}
