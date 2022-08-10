package app.EasyFoodAPI.dto;

import javax.validation.constraints.*;

public class FullProductInfoDTO {
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

    @Size(min = 3, max = 500, message = "Description must be between 3 and 500 characters")
    private String description;

    private Integer likesCount;

    @Size(min = 3, max = 30, message = "Brand name must be between 3 and 30 characters")
    private String brand;

    @Size(min = 3, max = 30, message = "Country name must be between 3 and 30 characters")
    private String country;

    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
