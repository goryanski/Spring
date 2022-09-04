package app.EasyFoodAPI.dto;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CategoryDTO {
    private Integer id;
    private String name;

    // Override equals and hashCode for using stream().distinct() in FavoritesProductsService/getFavoritesProductsCategories()
    // explanation how to generate these methods - in the theory file: 04.Additional_Info.docx
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

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
