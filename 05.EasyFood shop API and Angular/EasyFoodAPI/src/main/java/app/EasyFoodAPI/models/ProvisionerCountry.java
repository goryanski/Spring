package app.EasyFoodAPI.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "provisioners_countries")
public class ProvisionerCountry {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min = 3, max = 30, message = "Country name must be between 3 and 30 characters")
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
