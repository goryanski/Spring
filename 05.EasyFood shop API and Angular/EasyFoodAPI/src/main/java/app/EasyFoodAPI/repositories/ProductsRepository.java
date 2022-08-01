package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
    //public List<Product> findProductsByCategory(Category category);
}
