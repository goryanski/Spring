package app.EasyFoodAPI.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "amount_in_storage")
    private Integer amountInStorage;

    @Column(name = "likes_count")
    private Integer likesCount;

    @Column(name = "is_weight_flexible")
    private boolean isWeightFlexible;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private ProvisionerCountry country;

    @ManyToOne
    @JoinColumn(name = "weight_measurement_id", referencedColumnName = "id")
    private WeightMeasurement measurement;

    @OneToMany(mappedBy = "product")
    private List<OrderedProduct> orderedProducts;

    @OneToMany(mappedBy = "product")
    private List<BasketProduct> basketProducts;

    @OneToMany(mappedBy = "product")
    private List<FavoriteProduct> favoriteProducts;


    public List<FavoriteProduct> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<FavoriteProduct> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

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

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProvisionerCountry getCountry() {
        return country;
    }

    public void setCountry(ProvisionerCountry country) {
        this.country = country;
    }

    public WeightMeasurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(WeightMeasurement measurement) {
        this.measurement = measurement;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(List<BasketProduct> basketProducts) {
        this.basketProducts = basketProducts;
    }
}
