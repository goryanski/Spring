package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.BrandDTO;
import app.EasyFoodAPI.dto.ProvisionerCountryDTO;
import app.EasyFoodAPI.services.BrandsService;
import app.EasyFoodAPI.services.ProvisionersCountriesService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/products-filter")
public class ProductsFilterController {
    private final ProvisionersCountriesService countriesService;
    private final BrandsService brandsService;

    public ProductsFilterController(ProvisionersCountriesService countriesService, BrandsService brandsService) {
        this.countriesService = countriesService;
        this.brandsService = brandsService;
    }

    @GetMapping("/countries")
    public List<ProvisionerCountryDTO> getAllCountries() {
        return countriesService.getAll();
    }

    @GetMapping("/brands")
    public List<BrandDTO> getAllBrands() {
        return brandsService.getAll();
    }
}
