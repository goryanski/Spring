package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.EditProductDTO;
import app.EasyFoodAPI.dto.responses.ProductLinkedDataResponseDTO;
import app.EasyFoodAPI.models.*;
import app.EasyFoodAPI.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
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
    private final PeopleRepository peopleRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public AdminsService(MapperService mapper,
                         ProductsRepository productsRepository,
                         ProvisionersCountriesRepository countriesRepository,
                         BrandsRepository brandsRepository,
                         CategoriesRepository categoriesRepository,
                         WeightMeasurementsRepository measurementsRepository,
                         PeopleRepository peopleRepository,
                         RolesRepository rolesRepository) {
        this.mapper = mapper;
        this.productsRepository = productsRepository;
        this.countriesRepository = countriesRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.measurementsRepository = measurementsRepository;
        this.peopleRepository = peopleRepository;
        this.rolesRepository = rolesRepository;
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


    @Transactional
    public void addOrEditProduct(EditProductDTO productDTO) {
        boolean isEditMode = productDTO.getId() != 0;
        Product product = isEditMode
                ? productsRepository.findById(productDTO.getId()).get() // already validated
                : new Product();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDiscount(productDTO.getDiscount());
        product.setAvailable(productDTO.getAvailable());
        product.setPhotoPath(productDTO.getPhotoPath());
        product.setAmountInStorage(productDTO.getAmountInStorage());
        product.setWeightFlexible(productDTO.getWeightFlexible());


        // if it is edit mode - for category field we have to check if user changed category (if not - nothing to do)
        if(!isEditMode || !product.getCategory().getName().equals(productDTO.getCategoryName())) {
            // category was changed - find out if user changed category to another category that is in DB
            Optional<Category> changedCategory = categoriesRepository.findByName(productDTO.getCategoryName());
            if(changedCategory.isPresent()) {
                // category already exists - set to product
                product.setCategory(changedCategory.get());
            } else {
                // otherwise - create a new category, save in DB and only after that set to product
                Category category = new Category();
                category.setName(productDTO.getCategoryName());
                Category newCategory = categoriesRepository.save(category);
                product.setCategory(newCategory);
            }
        }


        // do the same with other similar fields
        if(!isEditMode || !product.getBrand().getName().equals(productDTO.getBrandName())) {
            Optional<Brand> changedBrand = brandsRepository.findByName(productDTO.getBrandName());
            if(changedBrand.isPresent()) {
                product.setBrand(changedBrand.get());
            } else {
                Brand brand = new Brand();
                brand.setName(productDTO.getBrandName());
                Brand newBrand = brandsRepository.save(brand);
                product.setBrand(newBrand);
            }
        }
        if(!isEditMode || !product.getCountry().getName().equals(productDTO.getCountryName())) {
            Optional<ProvisionerCountry> changedCountry = countriesRepository.findByName(productDTO.getCountryName());
            if(changedCountry.isPresent()) {
                product.setCountry(changedCountry.get());
            } else {
                ProvisionerCountry country = new ProvisionerCountry();
                country.setName(productDTO.getCountryName());
                ProvisionerCountry newCountry = countriesRepository.save(country);
                product.setCountry(newCountry);
            }
        }
        if(!isEditMode || !product.getMeasurement().getName().equals(productDTO.getMeasurementName())) {
            Optional<WeightMeasurement> changedMeasurement = measurementsRepository.findByName(productDTO.getMeasurementName());
            if(changedMeasurement.isPresent()) {
                product.setMeasurement(changedMeasurement.get());
            } else {
                WeightMeasurement measurement = new WeightMeasurement();
                measurement.setName(productDTO.getMeasurementName());
                WeightMeasurement newMeasurement = measurementsRepository.save(measurement);
                product.setMeasurement(newMeasurement);
            }
        }
        // if adding - save product, if editing - data already updated
        if(!isEditMode) {
            product.setLikesCount(0);
            productsRepository.save(product);
        }
    }

    @Transactional
    public String blockUser(Integer userId) {
        Optional<Person> person = peopleRepository.findById(userId);
        if(person.isPresent()) {
            person.get().setBlocked(true);
            return "OK";
        } else {
            return "User not found";
        }
    }

    @Transactional
    public String setUserAsAdmin(Integer userId) {
        Optional<Person> person = peopleRepository.findById(userId);
        if(person.isPresent()) {
            Role role = rolesRepository.findByName("ROLE_ADMIN");
            person.get().getAccount().setRole(role);
            return "OK";
        } else {
            return "User not found";
        }
    }
}
