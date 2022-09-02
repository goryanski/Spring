package app.EasyFoodAPI.dto;

public class BasketProductDTO {
    // instead of id we'll use productId and userId - as one composite id key (because we already have methods, which add, update and delete products in basket by productId and userId together)
    private Integer productId;
    private Integer userId;
    private String name;
    private String photoPath;
    private Float weight;
    private String weightMeasurement;
    private Boolean isWeightFlexible;
    private Float pricePerOneItem;
    private Float generalCount;
    private Float generalPrice;
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
