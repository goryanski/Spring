package app.EasyFoodAPI.dto.responses;
import app.EasyFoodAPI.dto.CategoryDTO;
import java.util.List;


public class HomePageResponse {
    List<CategoryDTO> categories;

    public HomePageResponse(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
