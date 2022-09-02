package app.EasyFoodAPI.dto.requests;

import javax.validation.constraints.NotNull;

public class BasketProductRequestDTO {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer productId;
    @NotNull
    private Float count;
    @NotNull
    private Boolean weightFlexible;



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Boolean getWeightFlexible() {
        return weightFlexible;
    }

    public void setWeightFlexible(Boolean weightFlexible) {
        this.weightFlexible = weightFlexible;
    }
}
