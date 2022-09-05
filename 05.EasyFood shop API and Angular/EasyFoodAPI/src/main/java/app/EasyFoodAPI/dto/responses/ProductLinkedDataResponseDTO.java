package app.EasyFoodAPI.dto.responses;

import java.util.List;

public class ProductLinkedDataResponseDTO {
    private List<String> categories;
    private List<String> countries;
    private List<String> brands;
    private List<String> weightMeasurements;


    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public List<String> getWeightMeasurements() {
        return weightMeasurements;
    }

    public void setWeightMeasurements(List<String> weightMeasurements) {
        this.weightMeasurements = weightMeasurements;
    }
}