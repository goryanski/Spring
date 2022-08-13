package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.FullProductInfoDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByCategoryRequestObjectDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByNameRequestObjectDTO;
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

    public Map<String, Object> getProductsByCategoryId(ProductsByCategoryRequestObjectDTO params) {
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
        // get first the most unpopular or new products with category like selected product category
        // (only unpopular or new products have the least likesCount value)
         return productsRepository.findByCategoryIdOrderByLikesCount(categoryId)
                 .stream()
                 .limit(productsCount)
                 .map(mapper::convertProduct)
                 .collect(Collectors.toList());
    }

    public Map<String, Object> getProductsBySubstringOfName(ProductsByNameRequestObjectDTO params) {
        Pageable paging = PageRequest.of(params.getCurrentPage(), params.getPageSize());
        // get all products which names contain required substring
        Page<Product> pageTuts = productsRepository.findByNameContainingIgnoreCase(params.getName(), paging);
        return getPaginatedResponse(pageTuts);
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
