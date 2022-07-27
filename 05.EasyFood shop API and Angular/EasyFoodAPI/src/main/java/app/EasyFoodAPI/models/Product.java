package app.EasyFoodAPI.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @Column(name = "description")
    @Size(min = 3, max = 500, message = "Description must be between 3 and 500 characters")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Price cannot be empty")
    @Digits(integer=9, fraction=2, message = "Number of allowed digits in the integral part - 9 and fraction part - 2 (example: 123.00)")
    private Float price;
}
