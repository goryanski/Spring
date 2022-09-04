package app.EasyFoodAPI.dto.requests;

public class FavoriteProductRequestDTO {
    private Integer userId;
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

