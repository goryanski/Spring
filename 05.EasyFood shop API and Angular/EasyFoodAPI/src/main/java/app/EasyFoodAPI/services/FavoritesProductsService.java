package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.dto.requests.FavoriteProductRequestDTO;
import app.EasyFoodAPI.dto.requests.FavoriteProductsByCategoryIdRequestDTO;
import app.EasyFoodAPI.models.FavoriteProduct;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.FavoritesProductsRepository;
import app.EasyFoodAPI.repositories.PeopleRepository;
import app.EasyFoodAPI.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FavoritesProductsService {
    private final MapperService mapper;
    private final FavoritesProductsRepository favoritesProductsRepository;
    private final PeopleRepository peopleRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public FavoritesProductsService(MapperService mapper, FavoritesProductsRepository favoritesProductsRepository,
                                    PeopleRepository peopleRepository,
                                    ProductsRepository productsRepository) {
        this.mapper = mapper;
        this.favoritesProductsRepository = favoritesProductsRepository;
        this.peopleRepository = peopleRepository;
        this.productsRepository = productsRepository;
    }

    public boolean isProductFavorite(FavoriteProductRequestDTO params) {
        return favoritesProductsRepository
                .findByPersonIdAndProductId(params.getUserId(), params.getProductId())
                .isPresent();
    }


    @Transactional
    public String addProductToFavorites(FavoriteProductRequestDTO params) {
        if(!isProductFavorite(params)) {
            Optional<Person> person = peopleRepository.findById(params.getUserId());
            Optional<Product> product = productsRepository.findById(params.getProductId());
            if(person.isPresent() && product.isPresent()) {
                Product productEntity = product.get();
                FavoriteProduct newProduct = new FavoriteProduct();
                newProduct.setPerson(person.get());
                newProduct.setProduct(productEntity);
                favoritesProductsRepository.save(newProduct);

                // change likes count in product entity
                productEntity.setLikesCount(productEntity.getLikesCount() + 1);
                return "OK";
            } else {
                return  "Wrong data";
            }
        } else {
            return  "Product already in favorites";
        }
    }

    @Transactional
    public String deleteProductFromFavorites(FavoriteProductRequestDTO params) {
        Optional<FavoriteProduct> favoriteProduct = favoritesProductsRepository
                .findByPersonIdAndProductId(params.getUserId(), params.getProductId());
        if(favoriteProduct.isPresent()) {
            favoritesProductsRepository.deleteById(favoriteProduct.get().getId());

            // change likes count in product entity
            Optional<Product> product = productsRepository.findById(params.getProductId());
            product.ifPresent(value -> value.setLikesCount(value.getLikesCount() - 1));
            return "OK";
        } else {
            return  "Product not in favorites";
        }
    }

    public List<CategoryDTO> getFavoritesProductsCategories(int userId) {
        List<FavoriteProduct> products = favoritesProductsRepository.findByPersonId(userId);
        List<CategoryDTO> categories = new ArrayList<>();
        products.forEach(product -> categories
                .add(mapper.convertCategory(product.getProduct().getCategory())
        ));
        return categories.stream()
                .distinct() //  Override equals() and hashCode() in CategoryDTO to use it
                .collect(Collectors.toList());
    }

    public Map<String, Object> getFavoriteProductsByCategoryId(FavoriteProductsByCategoryIdRequestDTO params) {
        // get user's favorite products
        List<FavoriteProduct> favoriteProducts = favoritesProductsRepository.findByPersonId(params.getUserId());

        // get products with type Product, which have required categoryId
        List<Product> productsEntities = new ArrayList<>();
        favoriteProducts.forEach(product -> {
            if(product.getProduct().getCategory().getId() == params.getCategoryId()) {
                productsEntities.add(product.getProduct());
            }
        });


        // build response with paginated products and other data
        Map<String, Object> response = new HashMap<>();
        if(productsEntities.size() > 0) {
            // pagination and mapping
            List<ShortProductInfoDTO> selectedProducts = productsEntities
                    .stream()
                    .skip(params.getSkip())
                    .limit(params.getLimit())
                    .map(mapper::convertProduct)
                    .collect(Collectors.toList());

            // form response
            response.put("products", selectedProducts);
            response.put("totalItems", productsEntities.size());
        } else {
            response.put("products", Collections.emptyList());
            response.put("totalItems", 0);
        }
        // these fields won't be use now, it's just to give the client more options for pagination logic in the future
        response.put("currentPage", 0);
        response.put("totalPages", 0);

        return response;
    }
}
