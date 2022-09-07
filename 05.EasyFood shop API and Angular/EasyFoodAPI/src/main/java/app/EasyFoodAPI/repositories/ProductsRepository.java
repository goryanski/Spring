package app.EasyFoodAPI.repositories;
import app.EasyFoodAPI.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryIdAndIsAvailableAndAmountInStorageGreaterThan(
            int categoryId,
            boolean isAvailable,
            int amount,
            Pageable pageable);

    // we use pagination to get only a few first products (in our case - 4 first products)
    Page<Product> findByCategoryIdAndIsAvailableAndAmountInStorageGreaterThanOrderByLikesCount(
            int categoryId,
            boolean isAvailable,
            int amount,
            Pageable pageable); // or we could use another way:
    //List<Product> findTop4ByCategoryIdOrderByLikesCount(int categoryId); - find top 4 from result, but in this case
    // we cannot change how many products we can take (we want to decide it on the client side)

    Page<Product> findByNameContainingIgnoreCaseAndIsAvailableAndAmountInStorageGreaterThan(
            String name,
            boolean isAvailable,
            int amount,
            Pageable pageable);

    // filter
    List<Product> findByBrandIdAndIsAvailableAndAmountInStorageGreaterThan(
            int brandId,
            boolean isAvailable,
            int amount);
    List<Product> findByCountryIdAndIsAvailableAndAmountInStorageGreaterThan(
            int countryId,
            boolean isAvailable,
            int amount);
    List<Product> findByDiscountGreaterThanAndIsAvailableAndAmountInStorageGreaterThan(
            int discount,
            boolean isAvailable,
            int amount);
    List<Product> findByPriceLessThanEqualAndIsAvailableAndAmountInStorageGreaterThan(
            float price,
            boolean isAvailable,
            int amount);



    
    // test
    //List<Product> findByCountryIdAndBrandIdNot(int country, int brandId);
}
