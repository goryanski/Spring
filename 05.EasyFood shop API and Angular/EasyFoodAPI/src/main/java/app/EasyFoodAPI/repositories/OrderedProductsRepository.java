package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductsRepository extends JpaRepository<OrderedProduct, Integer> {
}
