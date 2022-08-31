package app.EasyFoodAPI.dto.requests;

import javax.validation.constraints.NotNull;

public class UpdateBasketProductRequestDTO {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    private Float generalCount;

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
