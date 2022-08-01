package app.EasyFoodAPI.services;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.CategoriesRepository;
import app.EasyFoodAPI.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductsService {
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public ProductsService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Product> getProductsByCategoryId(int id) {
        Optional<Category> category = categoriesRepository.findById(id);
        if(category.isPresent()) {
            return category.get().getProducts();
        }
        // if wrong id - just send an empty list to avoid exception handle
        return Collections.emptyList();
    }

}
