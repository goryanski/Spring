package app.EasyFoodAPI.dto.requests;

public class RunOutProductsRequestDTO {
    private int leftInStockLimit;
    private int currentPage;
    private int pageSize;

    public int getLeftInStockLimit() {
        return leftInStockLimit;
    }

    public void setLeftInStockLimit(int leftInStockLimit) {
        this.leftInStockLimit = leftInStockLimit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
