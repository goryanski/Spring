package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.BrandDTO;
import app.EasyFoodAPI.dto.ProvisionerCountryDTO;
import app.EasyFoodAPI.models.Brand;
import app.EasyFoodAPI.repositories.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BrandsService {
    private final BrandsRepository brandsRepository;
    private final MapperService mapper;

    @Autowired
    public BrandsService(BrandsRepository brandsRepository, MapperService mapper) {
        this.brandsRepository = brandsRepository;
        this.mapper = mapper;
    }

    public List<BrandDTO> getAll() {
        return brandsRepository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::convertBrand)
                .collect(Collectors.toList());
    }
}