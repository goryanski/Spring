package app.EasyFoodAPI.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasketProductDTO {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    private Float count;

    @NotNull
    private Float generalPrice;

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

    public Float getGeneralPrice() {
        return generalPrice;
    }

    public void setGeneralPrice(Float generalPrice) {
        this.generalPrice = generalPrice;
    }
}
