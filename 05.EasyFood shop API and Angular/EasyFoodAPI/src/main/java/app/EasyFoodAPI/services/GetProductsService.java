package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.FullProductInfoDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.dto.requestObjects.FilterProductsRequestDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByCategoryRequestDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByNameRequestDTO;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.CategoriesRepository;
import app.EasyFoodAPI.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetProductsService {
    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;
    private final MapperService mapper;

    @Autowired
    public GetProductsService(CategoriesRepository categoriesRepository, ProductsRepository productsRepository, MapperService mapper) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
        this.mapper = mapper;
    }

    public Map<String, Object> getProductsByCategoryId(ProductsByCategoryRequestDTO params) {
        Pageable paging = PageRequest.of(params.getCurrentPage(), params.getPageSize());
        Page<Product> pageTuts = productsRepository.findByCategoryId(params.getCategoryId(), paging);
        return getPaginatedResponse(pageTuts);
    }

    public FullProductInfoDTO getProductById(int id) {
        return productsRepository.findById(id)
                .map(mapper::convertFullProduct)
                .orElse(null);
    }

    public List<ShortProductInfoDTO> getSimilarProducts(int categoryId, int productsCount) {
        // get first the most unpopular or new products by required categoryId
        // (only unpopular or new products have the least likesCount value)
        // for getting the first products we'll use pagination (explanation in the repository)
        return productsRepository.findByCategoryIdOrderByLikesCount(categoryId,
                        PageRequest.of(0, productsCount))
                .getContent()
                .stream()
                // .limit(productsCount) we also can use limit here. In this way we don't need to get paginated result
                // from repository, but it's not as efficient because we get all products from DB and only after that
                // take only 4 from them
                .map(mapper::convertProduct)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getProductsBySubstringOfName(ProductsByNameRequestDTO params) {
        Pageable paging = PageRequest.of(params.getCurrentPage(), params.getPageSize());
        // get all products which names contain required substring
        Page<Product> pageTuts = productsRepository.findByNameContainingIgnoreCase(params.getName(), paging);
        return getPaginatedResponse(pageTuts);
    }


    public Map<String, Object> getFilteredProducts(FilterProductsRequestDTO params) {
        List<Product> filteredProducts;
        // first try to filter by brandId to significantly reduce the number of products we take from DB
        if(params.getBrandId() != 0) {
            filteredProducts = productsRepository.findByBrandId(params.getBrandId());
            // then check other filter params and do filter if it's required
            if(params.getCountryId() != 0) {
                filteredProducts.removeIf(p -> p.getCountry().getId() != params.getCountryId());
            }
            if(params.isWithDiscount()) {
                filteredProducts.removeIf(p -> p.getDiscount() == 0);
            }
            filteredProducts.removeIf(p -> p.getPrice() > params.getMaxPrice());
        } else {
            // if brandId = 0, start filter with country
            if(params.getCountryId() != 0) {
                filteredProducts = productsRepository.findByCountryId(params.getCountryId());
                if(params.isWithDiscount()) {
                    filteredProducts.removeIf(p -> p.getDiscount() == 0);
                }
                filteredProducts.removeIf(p -> p.getPrice() > params.getMaxPrice());
            } else {
                // if brandId = 0 and country id = 0, start with discount
                if(params.isWithDiscount()) {
                    filteredProducts = productsRepository.findByDiscountGreaterThan(0);
                    filteredProducts.removeIf(p -> p.getPrice() > params.getMaxPrice());
                }
                else {
                    // if brandId = 0, country id = 0 and discountProducts = false, start with price
                    filteredProducts = productsRepository.findByPriceLessThanEqual(params.getMaxPrice());
                }
            }
        }
        // after that we definitely initialize filteredProducts (if brandId = 0, country id = 0 and
        // discountProducts = false, we still filter by price, and we don't need to get all products from DB).
        // Of course, we could all code above replace with:
//        filteredProducts = productsRepository.findAll();
//        if(params.getBrandId() != 0) {
//            filteredProducts.removeIf(p -> p.getBrand().getId() != params.getBrandId());
//        }
//        if(params.getCountryId() != 0) {
//            filteredProducts.removeIf(p -> p.getCountry().getId() != params.getCountryId());
//        }
//        if(params.isWithDiscount()) {
//            filteredProducts.removeIf(p -> p.getDiscount() == 0);
//        }
//        filteredProducts.removeIf(p -> p.getPrice() > params.getMaxPrice());

        // that is, get all products and then just remove by filters. It would be less code and more clear, but getting
        // all products from DB first is not very rational - even if user didn't choose any filter option, at least we
        // reduce the number of products with price  and query will execute faster (on client side default price value = 600)


        Map<String, Object> response = new HashMap<>();
        if(filteredProducts.size() > 0) {
            if(params.isPopularFirst()) {
                // sorting
                filteredProducts = filteredProducts.stream()
                        .sorted(Comparator.comparingInt(Product::getLikesCount)
                        .reversed())
                        .collect(Collectors.toList());
            }
            // pagination and mapping
            List<ShortProductInfoDTO> selectedProducts = filteredProducts
                    .stream()
                    .skip(params.getSkip())
                    .limit(params.getLimit())
                    .map(mapper::convertProduct)
                    .collect(Collectors.toList());

            // form response
            response.put("products", selectedProducts);
            response.put("totalItems", filteredProducts.size());
        } else {
            response.put("products", Collections.emptyList());
            response.put("totalItems", 0);
        }
        // these fields won't be use now, it's just to give the client more options for pagination logic in the future
        response.put("currentPage", 0);
        response.put("totalPages", 0);

        return response;
    }


    private Map<String, Object> getPaginatedResponse(Page<Product> pageTuts) {
        List<ShortProductInfoDTO> products = pageTuts.getContent()
                .stream()
                .map(mapper::convertProduct)
                .collect(Collectors.toList());;

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
