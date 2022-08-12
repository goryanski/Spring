package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final MapperService mapper;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository, MapperService mapper) {
        this.categoriesRepository = categoriesRepository;
        this.mapper = mapper;
    }

    public List<CategoryDTO> getAll() {
        return categoriesRepository.findAll()
                .stream()
                .map(mapper::convertCategory)
                .collect(Collectors.toList());
    }
}
