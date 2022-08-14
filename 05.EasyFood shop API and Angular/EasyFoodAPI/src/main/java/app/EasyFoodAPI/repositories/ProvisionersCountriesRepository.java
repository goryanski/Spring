package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.ProvisionerCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvisionersCountriesRepository extends JpaRepository<ProvisionerCountry, Integer> {
    List<ProvisionerCountry> findAllByOrderByNameAsc();
}
