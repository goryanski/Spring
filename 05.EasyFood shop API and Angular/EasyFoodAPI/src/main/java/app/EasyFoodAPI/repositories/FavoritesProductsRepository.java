package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavoritesProductsRepository extends JpaRepository<FavoriteProduct, Integer> {
    Optional<FavoriteProduct> findByPersonIdAndProductId(int personId, int productId);
    List<FavoriteProduct> findByPersonId(int personId);
}
