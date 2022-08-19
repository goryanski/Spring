package app.EasyFoodAPI.dto.requestObjects;

public class FilterProductsRequestDTO {
    private int countryId;
    private int brandId;
    private int maxPrice;
    private boolean withDiscount;
    private boolean popularFirst;
    private int skip;
    private int limit;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isWithDiscount() {
        return withDiscount;
    }

    public void setWithDiscount(boolean withDiscount) {
        this.withDiscount = withDiscount;
    }

    public boolean isPopularFirst() {
        return popularFirst;
    }

    public void setPopularFirst(boolean popularFirst) {
        this.popularFirst = popularFirst;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
