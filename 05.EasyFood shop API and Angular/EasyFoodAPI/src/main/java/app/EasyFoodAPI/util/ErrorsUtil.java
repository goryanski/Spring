package app.EasyFoodAPI.util;
import app.EasyFoodAPI.util.exceptions.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

// separate class with static method which returns validation errors to the client - one method for all controllers
public class ErrorsUtil {
    public static void returnRegistrationErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = getErrorMessage(bindingResult);
        // pass this string to custom exception class
        throw new AuthException(errorMessage.toString());
    }

    public static void returnAddProductToBasketErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = getErrorMessage(bindingResult);
        throw new AddProductToBasketException(errorMessage.toString());
    }

    public static void returnMakeOrderErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = getErrorMessage(bindingResult);
        throw new MakeOrderException(errorMessage.toString());
    }

    public static void returnUpdatePersonErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = getErrorMessage(bindingResult);
        throw new UpdatePersonException(errorMessage.toString());
    }

    public static void returnProductValidationErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = getErrorMessage(bindingResult);
        throw new ProductValidationException(errorMessage.toString());
    }



    private static StringBuilder getErrorMessage(BindingResult bindingResult) {
        // form all errors to one string
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append("; ");
        }
        return errorMessage;
    }
}
