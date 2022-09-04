package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.BrandDTO;
import app.EasyFoodAPI.dto.ProvisionerCountryDTO;
import app.EasyFoodAPI.dto.requests.FilterProductsRequestDTO;
import app.EasyFoodAPI.services.BrandsService;
import app.EasyFoodAPI.services.GetProductsService;
import app.EasyFoodAPI.services.ProvisionersCountriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/products-filter")
public class ProductsFilterController {
    private final ProvisionersCountriesService countriesService;
    private final BrandsService brandsService;
    private final GetProductsService productsService;

    public ProductsFilterController(ProvisionersCountriesService countriesService, BrandsService brandsService, GetProductsService productsService) {
        this.countriesService = countriesService;
        this.brandsService = brandsService;
        this.productsService = productsService;
    }

    @GetMapping("/countries")
    public List<ProvisionerCountryDTO> getAllCountries() {
        return countriesService.getAll();
    }

    @GetMapping("/brands")
    public List<BrandDTO> getAllBrands() {
        return brandsService.getAll();
    }

    @PostMapping("/filtered-products")
    public ResponseEntity<Map<String, Object>> getFilteredProducts(
            @RequestBody FilterProductsRequestDTO params) {
        Map<String, Object> response = productsService.getFilteredProducts(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
