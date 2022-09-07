package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.WeightMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightMeasurementsRepository extends JpaRepository<WeightMeasurement, Integer> {
    Optional<WeightMeasurement> findByName(String name);
}
