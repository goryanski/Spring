package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.EditProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminsService {
    private final MapperService mapper;

    @Autowired
    public AdminsService(MapperService mapper) {
        this.mapper = mapper;
    }

    public EditProductDTO getProductToEdit(int id) {


        return null;
    }
}
