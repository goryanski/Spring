package app.EasyFoodAPI.util.exceptions;

public class ProductValidationException extends RuntimeException{
    public ProductValidationException(String msg) {
        super(msg);
    }
}
