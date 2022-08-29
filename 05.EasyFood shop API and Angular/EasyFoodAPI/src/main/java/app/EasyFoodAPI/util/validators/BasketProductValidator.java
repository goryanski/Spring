package app.EasyFoodAPI.util.validators;
import app.EasyFoodAPI.dto.requestObjects.BasketProductRequestDTO;
import app.EasyFoodAPI.services.GetProductsService;
import app.EasyFoodAPI.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BasketProductValidator implements Validator {
    private final PeopleService peopleService;
    private final GetProductsService productsService;

    public BasketProductValidator(PeopleService peopleService, GetProductsService productsService) {
        this.peopleService = peopleService;
        this.productsService = productsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BasketProductRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BasketProductRequestDTO product = (BasketProductRequestDTO) target;
        // check if user and products exist
        if(peopleService.getPersonById(product.getUserId()).isEmpty()) {
            errors.rejectValue("userId", "", "User not found");
        }
        if(productsService.getProductById(product.getProductId()).isEmpty()) {
            errors.rejectValue("productId", "", "Product not found");
        }


        // validate product count
        // field product.weightFlexible – we need it to know how we have to change count of products when user makes order (if it’s wine – we can only add +1 bottle, but if it’s the potatoes – we want to add 1.5 kg or even 700 g)
        if(!product.getWeightFlexible()) {
            // make sure there is whole number (int) in a value
            Integer intValue = Math.round(product.getCount());
            System.out.println("intValue: " + intValue);
            if(intValue > 100) {
                errors.rejectValue("count", "", "Max count value is 100");
            } else if(intValue < 1) {
                errors.rejectValue("count", "", "Min count value is 1");
            } else {
                product.setCount(intValue.floatValue());
            }
        } else {
            // make sure there is one digit after point in a value
            String stringValue = String.format("%.1f", product.getCount());
            Float floatValue = Float.parseFloat(stringValue);
            if(floatValue > 100) {
                errors.rejectValue("count", "", "Max count value is 100");
            } else if(floatValue < 0.5) {
                errors.rejectValue("count", "", "Min count value is 0.5");
            } else {
                product.setCount(floatValue);
            }
        }
    }
}
