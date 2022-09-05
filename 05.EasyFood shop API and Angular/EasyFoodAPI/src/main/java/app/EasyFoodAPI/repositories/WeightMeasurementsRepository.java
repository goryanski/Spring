package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.WeightMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightMeasurementsRepository extends JpaRepository<WeightMeasurement, Integer> {

}
