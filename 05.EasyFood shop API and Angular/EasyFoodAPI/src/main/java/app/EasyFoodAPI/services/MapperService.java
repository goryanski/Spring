package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.*;
import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO convertCategory(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public ShortProductInfoDTO convertProduct(Product product) {
        ShortProductInfoDTO productDTO = modelMapper.map(product, ShortProductInfoDTO.class);
        // in product field measurement is object, but we need to set in productDTO only name of measurement
        productDTO.setWeightMeasurement(product.getMeasurement().getName());
        return productDTO;
    }

    public FullProductInfoDTO convertFullProduct(Product product) {
        FullProductInfoDTO productDTO = modelMapper.map(product, FullProductInfoDTO.class);
        // change fields which need to map manually
        productDTO.setWeightMeasurement(product.getMeasurement().getName());
        productDTO.setBrand(product.getBrand().getName());
        productDTO.setCountry(product.getCountry().getName());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    public ProvisionerCountryDTO convertCountry(ProvisionerCountry country) {
        return modelMapper.map(country, ProvisionerCountryDTO.class);
    }

    public BrandDTO convertBrand(Brand brand) {
        return modelMapper.map(brand, BrandDTO.class);
    }


}
