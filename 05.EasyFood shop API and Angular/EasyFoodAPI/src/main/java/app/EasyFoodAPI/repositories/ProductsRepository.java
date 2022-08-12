package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByCategoryId(int categoryId, Pageable pageable);

    List<Product> findByCategoryIdOrderByLikesCount(int categoryId);
}
