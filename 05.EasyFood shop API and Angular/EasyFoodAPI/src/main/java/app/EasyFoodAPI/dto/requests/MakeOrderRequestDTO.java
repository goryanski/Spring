package app.EasyFoodAPI.dto.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MakeOrderRequestDTO {
    @NotNull
    private Integer userId;

    @NotNull
    @Min(value = 0L, message = "price must be positive")
    private Float basketPrice;

    @NotNull
    @NotEmpty(message = "phone number cannot be empty")
    private String phoneNumber;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(Float basketPrice) {
        this.basketPrice = basketPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
