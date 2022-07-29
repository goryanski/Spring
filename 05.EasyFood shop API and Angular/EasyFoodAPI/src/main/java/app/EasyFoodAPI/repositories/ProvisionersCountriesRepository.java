package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.ProvisionerCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisionersCountriesRepository extends JpaRepository<ProvisionerCountry, Integer> {
}
