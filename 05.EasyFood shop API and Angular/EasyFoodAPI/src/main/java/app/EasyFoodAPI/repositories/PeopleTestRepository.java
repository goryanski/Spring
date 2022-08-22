package app.EasyFoodAPI.repositories;

import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.models.PersonTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleTestRepository extends JpaRepository<PersonTest, Integer> {
    Optional<PersonTest> findByUsername(String username);
}
