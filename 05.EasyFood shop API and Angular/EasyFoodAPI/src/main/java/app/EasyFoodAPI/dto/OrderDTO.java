package app.EasyFoodAPI.dto;

import javax.validation.constraints.NotNull;

public class OrderDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String date;
    @NotNull
    private Float price;
    @NotNull
    private String state;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}