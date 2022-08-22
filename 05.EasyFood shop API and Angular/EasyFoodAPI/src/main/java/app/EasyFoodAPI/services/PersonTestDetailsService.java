package app.EasyFoodAPI.services;

import app.EasyFoodAPI.models.PersonTest;
import app.EasyFoodAPI.repositories.PeopleTestRepository;
import app.EasyFoodAPI.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // this service for spring security
public class PersonTestDetailsService implements UserDetailsService {
    private final PeopleTestRepository peopleTestRepository;

    @Autowired
    public PersonTestDetailsService(PeopleTestRepository peopleTestRepository) {
        this.peopleTestRepository = peopleTestRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PersonTest> person = peopleTestRepository.findByUsername(username);
        if(person.isEmpty()) {
            // will be caught by spring security
            throw new UsernameNotFoundException("User not found");
        }
        // if there is a person in db with this username - wrap with PersonDetails and return it
        return new PersonDetails(person.get());
    }
}
