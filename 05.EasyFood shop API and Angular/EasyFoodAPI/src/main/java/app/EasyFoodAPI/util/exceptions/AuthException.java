package app.EasyFoodAPI.util.exceptions;

public class AuthException extends RuntimeException {
    public AuthException(String msg) {
        super(msg);
    }
}