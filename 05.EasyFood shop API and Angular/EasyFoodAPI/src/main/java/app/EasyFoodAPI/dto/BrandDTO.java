package app.EasyFoodAPI.dto;
import javax.validation.constraints.Size;

public class BrandDTO {
    private Integer id;

    @Size(min = 3, max = 30, message = "Brand name must be between 3 and 30 characters")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
