package app.EasyFoodAPI.services;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.CategoriesRepository;
import app.EasyFoodAPI.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductsService {
    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(CategoriesRepository categoriesRepository, ProductsRepository productsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
    }

    public List<Product> getProductsByCategoryId(int categoryId,
                                                 int skippedElementsCount,
                                                 int itemsPerPageCount ) {
        return categoriesRepository.findById(categoryId)
                .map(value -> value.getProducts()
                .stream()
                .skip(skippedElementsCount)
                .limit(itemsPerPageCount)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList()); // if wrong id - just return an empty list
    }

    public long getAllProductsCountByCategoryId(int id) {
        return categoriesRepository.findById(id)
                .map(value -> value.getProducts().size())
                .orElse(0); // if wrong id - just return 0
    }
}
