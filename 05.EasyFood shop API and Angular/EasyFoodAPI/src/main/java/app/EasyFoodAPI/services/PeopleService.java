package app.EasyFoodAPI.services;

import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final MapperService mapper;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, MapperService mapper) {
        this.peopleRepository = peopleRepository;
        this.mapper = mapper;
    }

    public Optional<Person> getPersonByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }
}
