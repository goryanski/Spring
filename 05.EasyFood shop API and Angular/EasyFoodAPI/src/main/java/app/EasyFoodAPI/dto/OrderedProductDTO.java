package app.EasyFoodAPI.dto;

import javax.validation.constraints.NotNull;

public class OrderedProductDTO {
    private Integer originalProductId;
    private String name;
    private Float count;
    private Float price;


    public Integer getOriginalProductId() {
        return originalProductId;
    }

    public void setOriginalProductId(Integer originalProductId) {
        this.originalProductId = originalProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}