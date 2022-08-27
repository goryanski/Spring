package app.EasyFoodAPI.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

public class ShortProductInfoDTO {
    private Integer id;

    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Price cannot be empty")
    @Digits(integer=9, fraction=2, message = "Max number of allowed digits in the integral part - 9 and fraction part - 2 (example: 123.00)")
    private Float price;

    @NotNull(message = "Weight cannot be empty")
    @Digits(integer=7, fraction=2, message = "Max number of allowed digits in the integral part - 7 and fraction part - 2 (example: 123.00)")
    private Float weight;

    private String weightMeasurement;

    @NotNull(message = "Discount cannot be empty. If there is no discount - set 0")
    @Min(value = 0, message = "Discount cannot be less than 0%")
    @Max(value = 99, message = "Discount cannot be more than 99%")
    private Integer discount;

    @NotEmpty(message = "Photo path cannot be empty")
    private String photoPath;

    private boolean isWeightFlexible;


    public boolean isWeightFlexible() {
        return isWeightFlexible;
    }

    public void setWeightFlexible(boolean weightFlexible) {
        isWeightFlexible = weightFlexible;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getWeightMeasurement() {
        return weightMeasurement;
    }

    public void setWeightMeasurement(String weightMeasurement) {
        this.weightMeasurement = weightMeasurement;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
