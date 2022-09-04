package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByPersonIdOrderByDateDesc(int id, Pageable pageable);
}
