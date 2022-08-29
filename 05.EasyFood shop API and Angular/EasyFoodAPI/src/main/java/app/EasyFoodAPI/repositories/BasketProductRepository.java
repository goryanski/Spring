package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.BasketProduct;
import app.EasyFoodAPI.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {
    Optional<BasketProduct> findByPersonIdAndProductId(int personId, int productId);
    List<BasketProduct> findByPersonId(int personId);
}
