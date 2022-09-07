package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Brand;
import app.EasyFoodAPI.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByOrderByNameAsc();
    Optional<Brand> findByName(String name);
}
