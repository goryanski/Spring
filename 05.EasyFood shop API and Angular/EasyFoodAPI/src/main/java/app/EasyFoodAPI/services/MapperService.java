package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.models.Category;
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
}
