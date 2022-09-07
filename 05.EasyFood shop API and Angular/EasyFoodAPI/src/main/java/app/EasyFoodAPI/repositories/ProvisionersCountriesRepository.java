package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.ProvisionerCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvisionersCountriesRepository extends JpaRepository<ProvisionerCountry, Integer> {
    List<ProvisionerCountry> findAllByOrderByNameAsc();
    Optional<ProvisionerCountry> findByName(String name);
}
