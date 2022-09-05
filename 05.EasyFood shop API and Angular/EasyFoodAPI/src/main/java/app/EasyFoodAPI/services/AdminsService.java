package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.EditProductDTO;
import app.EasyFoodAPI.dto.responses.ProductLinkedDataResponseDTO;
import app.EasyFoodAPI.models.Brand;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.ProvisionerCountry;
import app.EasyFoodAPI.models.WeightMeasurement;
import app.EasyFoodAPI.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminsService {
    private final MapperService mapper;
    private final ProductsRepository productsRepository;
    private final ProvisionersCountriesRepository countriesRepository;
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final WeightMeasurementsRepository measurementsRepository;

    @Autowired
    public AdminsService(MapperService mapper,
                         ProductsRepository productsRepository,
                         ProvisionersCountriesRepository countriesRepository,
                         BrandsRepository brandsRepository,
                         CategoriesRepository categoriesRepository,
                         WeightMeasurementsRepository measurementsRepository) {
        this.mapper = mapper;
        this.productsRepository = productsRepository;
        this.countriesRepository = countriesRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.measurementsRepository = measurementsRepository;
    }

    public EditProductDTO getProductToEdit(int id) {
       return productsRepository.findById(id)
                .map(mapper::convertProductToEdit)
                .orElse(null);
    }

    public ProductLinkedDataResponseDTO getProductLinkedData() {
        ProductLinkedDataResponseDTO data = new ProductLinkedDataResponseDTO();

        data.setCountries(countriesRepository.findAll()
                .stream()
                .map(ProvisionerCountry::getName)
                .collect(Collectors.toList()));
        data.setCategories(categoriesRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList()));
        data.setBrands(brandsRepository.findAll()
                .stream()
                .map(Brand::getName)
                .collect(Collectors.toList()));
        data.setWeightMeasurements(measurementsRepository.findAll()
                .stream()
                .map(WeightMeasurement::getName)
                .collect(Collectors.toList()));

        return data;
    }
}
