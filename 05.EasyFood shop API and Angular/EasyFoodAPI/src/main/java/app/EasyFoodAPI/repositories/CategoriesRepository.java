package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
