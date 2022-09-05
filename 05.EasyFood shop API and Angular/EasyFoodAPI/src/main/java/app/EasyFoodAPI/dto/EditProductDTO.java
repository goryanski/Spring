package app.EasyFoodAPI.dto;

public class EditProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Float weight;
    private Integer discount;
    private Boolean isAvailable;
    private String photoPath;
    private Integer amountInStorage;
    private Boolean isWeightFlexible;

    // if you need to convert Product to EditProductDTO with ModelMapper, and there are some fields with data from other tables, you have to name there fields (in EditProductDTO) like this (bellow). Example: in Product entity field "Category category" (class Category has fields "int id" and "String name"); If you name field "someCategory" in class EditProductDTO and you want to map in this field category.name from  Product,  instead of mapping manually (editProductDTO.setSomeCategory(product.getCategory().getName())),  ModelMapper can do it automatically if you name field like "categoryName" in EditProductDTO. The same you can do with id - just name field as "categoryId"
    private String categoryName;
    private String brandName;
    private String countryName;
    private String measurementName;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getAmountInStorage() {
        return amountInStorage;
    }

    public void setAmountInStorage(Integer amountInStorage) {
        this.amountInStorage = amountInStorage;
    }

    public Boolean getWeightFlexible() {
        return isWeightFlexible;
    }

    public void setWeightFlexible(Boolean weightFlexible) {
        isWeightFlexible = weightFlexible;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName = measurementName;
    }
}