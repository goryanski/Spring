package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.models.Category;
import app.EasyFoodAPI.models.Product;
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
}
