package app.EasyFoodAPI.services;

import app.EasyFoodAPI.dto.PersonDTO;
import app.EasyFoodAPI.dto.requests.UpdatePersonRequestDTO;
import app.EasyFoodAPI.models.Account;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.repositories.AccountsRepository;
import app.EasyFoodAPI.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final AccountsRepository accountsRepository;
    private final MapperService mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository,
                         AccountsRepository accountsRepository,
                         MapperService mapper,
                         PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.accountsRepository = accountsRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Person> getPersonByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    public Optional<Person> getPersonById(Integer id) {
        return peopleRepository.findById(id);
    }

    public PersonDTO getUserInfo(int id) {
        return peopleRepository.findById(id)
                .map(mapper::convertPerson)
                .orElse(null);
    }

    @Transactional
    public void editPersonInfo(UpdatePersonRequestDTO personDTO) {
        // we already checked id in UpdatePersonValidator, so person definitely exists
        Person person = peopleRepository.findById(personDTO.getId()).get();

        // check all fields for emptiness before updating. if the field is empty - no need to update.
        // create variables to make code cleaner
        boolean fullNameIsNotEmpty = !personDTO.getFullName().equals("");
        boolean emailIsNotEmpty = !personDTO.getEmail().equals("");
        boolean dateIsNotEmpty = !personDTO.getDateOfBirth().equals("");
        boolean usernameIsNotEmpty = !personDTO.getUsername().equals("");
        boolean passwordIsNotEmpty = !personDTO.getPassword().equals("");


        if(fullNameIsNotEmpty) {
            person.setFullName(personDTO.getFullName());
        }
        if(emailIsNotEmpty) {
            person.setEmail(personDTO.getEmail());
        }
        if(dateIsNotEmpty) {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(personDTO.getDateOfBirth());
                person.setDateOfBirth(date);
            } catch (Exception e) {
                // nothing to catch because we absolutely sure that date is valid - we check it in UpdatePersonValidator already, but we still need wrap it with try catch - parse() throws exception
            }
        }


        // update account (it's a separate entity)
        if(usernameIsNotEmpty || passwordIsNotEmpty) {
            Account account = accountsRepository.findById(person.getAccount().getId()).get();
            if(usernameIsNotEmpty) {
                account.setUsername(personDTO.getUsername());
            }
            if(passwordIsNotEmpty) {
                // encode password before saving to db
                account.setPassword(passwordEncoder.encode(personDTO.getPassword()));
            }
        }
    }
}
