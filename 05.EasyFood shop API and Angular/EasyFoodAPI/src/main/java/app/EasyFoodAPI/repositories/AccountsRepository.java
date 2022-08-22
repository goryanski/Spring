package app.EasyFoodAPI.repositories;

import app.EasyFoodAPI.models.Account;
import app.EasyFoodAPI.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
