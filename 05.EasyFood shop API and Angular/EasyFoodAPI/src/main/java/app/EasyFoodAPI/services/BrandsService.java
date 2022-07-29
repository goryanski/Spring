package app.EasyFoodAPI.services;
import app.EasyFoodAPI.models.Brand;
import app.EasyFoodAPI.repositories.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BrandsService {
    private final BrandsRepository brandsRepository;

    @Autowired
    public BrandsService(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    public List<Brand> getAll() {
        return brandsRepository.findAll();
    }
}