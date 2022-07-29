package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Integer> {
}
