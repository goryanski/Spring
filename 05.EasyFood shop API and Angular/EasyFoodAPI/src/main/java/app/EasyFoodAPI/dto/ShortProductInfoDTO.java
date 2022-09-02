package app.EasyFoodAPI.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

public class ShortProductInfoDTO {
    private Integer id;
    private String name;
    private Float price;
    private Float weight;
    private String weightMeasurement;
    private Integer discount;
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
