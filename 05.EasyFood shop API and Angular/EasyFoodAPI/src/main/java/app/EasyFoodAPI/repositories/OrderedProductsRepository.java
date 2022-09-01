package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductsRepository extends JpaRepository<OrderedProduct, Integer> {
    List<OrderedProduct> findByOrderId(int id);
}
