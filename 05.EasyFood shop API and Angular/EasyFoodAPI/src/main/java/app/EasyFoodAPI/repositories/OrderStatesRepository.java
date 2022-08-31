package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatesRepository extends JpaRepository<OrderState, Integer> {
    Optional<OrderState> findByName(String name);
}
