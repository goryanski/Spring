package app.EasyFoodAPI.dto.requests;

import javax.validation.constraints.NotNull;

public class RemoveBasketProductRequestDTO {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

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
}
