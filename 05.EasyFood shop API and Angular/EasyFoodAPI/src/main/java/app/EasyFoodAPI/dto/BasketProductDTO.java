package app.EasyFoodAPI.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasketProductDTO {
    // instead of id we'll use productId and userId - as one composite id key (because we already have methods, which add, update and delete products in basket by productId and userId together)
    @NotNull
    private Integer productId;
    @NotNull
    private Integer userId;
    @NotNull
    private String name;
    @NotNull
    private String photoPath;
    @NotNull
    private Float weight;
    @NotNull
    private String weightMeasurement;
    @NotNull
    private Boolean isWeightFlexible;
    @NotNull
    private Float pricePerOneItem;
    @NotNull
    private Float generalCount;
    @NotNull
    private Float generalPrice;
    @NotNull
    private Integer countInStorage;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountInStorage() {
        return countInStorage;
    }

    public void setCountInStorage(Integer countInStorage) {
        this.countInStorage = countInStorage;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public Boolean getWeightFlexible() {
        return isWeightFlexible;
    }

    public void setWeightFlexible(Boolean weightFlexible) {
        isWeightFlexible = weightFlexible;
    }

    public Float getPricePerOneItem() {
        return pricePerOneItem;
    }

    public void setPricePerOneItem(Float pricePerOneItem) {
        this.pricePerOneItem = pricePerOneItem;
    }

    public Float getGeneralCount() {
        return generalCount;
    }

    public void setGeneralCount(Float generalCount) {
        this.generalCount = generalCount;
    }

    public Float getGeneralPrice() {
        return generalPrice;
    }

    public void setGeneralPrice(Float generalPrice) {
        this.generalPrice = generalPrice;
    }
}
