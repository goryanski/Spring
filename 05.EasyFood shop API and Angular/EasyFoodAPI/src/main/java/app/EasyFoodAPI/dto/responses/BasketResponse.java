package app.EasyFoodAPI.dto.responses;

import app.EasyFoodAPI.dto.BasketProductDTO;

import java.util.List;

public class BasketResponse {
    private List<BasketProductDTO> products;
    private Double basketPrice;

    public BasketResponse(List<BasketProductDTO> products, Double basketPrice) {
        this.products = products;
        this.basketPrice = basketPrice;
    }

    public List<BasketProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<BasketProductDTO> products) {
        this.products = products;
    }

    public Double getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(Double basketPrice) {
        this.basketPrice = basketPrice;
    }
    //
//    public Float getBasketPrice() {
//        return basketPrice;
//    }
//
//    public void setBasketPrice(Float basketPrice) {
//        this.basketPrice = basketPrice;
//    }
}
