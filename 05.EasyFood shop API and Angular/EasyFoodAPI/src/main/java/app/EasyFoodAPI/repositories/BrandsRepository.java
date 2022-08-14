package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByOrderByNameAsc();
}
