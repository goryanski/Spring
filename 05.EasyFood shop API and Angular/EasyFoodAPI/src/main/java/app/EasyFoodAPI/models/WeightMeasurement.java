package app.EasyFoodAPI.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "weight_measurements")
public class WeightMeasurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min = 1, max = 4, message = "Measurement name must be between 1 and 4 characters")
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
