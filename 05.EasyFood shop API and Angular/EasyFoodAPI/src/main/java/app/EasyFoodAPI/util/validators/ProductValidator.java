package app.EasyFoodAPI.util.validators;
import app.EasyFoodAPI.dto.EditProductDTO;
import app.EasyFoodAPI.services.GetProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {
    private final GetProductsService productsService;

    @Autowired
    public ProductValidator(GetProductsService productsService) {
        this.productsService = productsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return EditProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditProductDTO product = (EditProductDTO) target;

        // if the product exists.
        // if product.getId() = 0 - we are adding product, otherwise - editing
        if(product.getId() != null && product.getId() != 0) {
            if(productsService.getProductById(product.getId()).isEmpty()) {
                errors.rejectValue("id", "", "Product not found");
            }
        }

        // next validate all fields one by one
        // string type fields
        // name
        if(product.getName().length() < 3 || product.getName().length() > 100) {
            errors.rejectValue("name", "", "Name length must be between 3 and 100 characters");
        }
        // description
        if(product.getDescription().length() < 3 || product.getDescription().length() > 500) {
            errors.rejectValue("description", "", "Description length must be between 3 and 500 characters");
        }
        // photoPath
        if(product.getPhotoPath().length() < 4 || product.getPhotoPath().length() > 600) {
            errors.rejectValue("photoPath", "", "Photo path length must be between 4 and 600 characters");
        }


        // number type fields
        // price
        if(product.getPrice() <= 0) {
            errors.rejectValue("price", "", "Price value must be more than 0");
        }
        // if price like 12.12345 - save to DB value only with 2 digits after point (12.12)
        String stringPriceValue = String.format("%.2f", product.getPrice());
        product.setPrice(Float.parseFloat(stringPriceValue));

        // weight
        if(product.getWeight() < 0) {
            errors.rejectValue("weight", "", "Weight value must be positive");
        }
        // the same as price
        product.setWeight(Float.parseFloat(String.format("%.2f", product.getWeight())));

        // discount
        if(product.getDiscount() < 0 || product.getDiscount() > 99) {
            errors.rejectValue("discount", "", "Discount must be between 0 and 99 characters");
        }
        product.setDiscount(Math.round(product.getDiscount())); // be sure that is a whole number

        // amountInStorage
        if(product.getAmountInStorage() < 0) {
            errors.rejectValue("amountInStorage", "", "Amount in storage value must be positive");
        }


        // fields to validation with regex
        // categoryName
        String categoryNamePattern = "^[A-Z]{1}[a-z ]{2,39}$";
        if(!product.getCategoryName().matches(categoryNamePattern)) {
            errors.rejectValue("categoryName", "",
                    "English letters only, space, first letter is capitalized, other - lowercase (3-40 characters)");
        }
        // brandName
        String brandNamePattern = "^[a-zA-Z 0-9.%#@&$]{3,30}$";
        if(!product.getBrandName().matches(brandNamePattern)) {
            errors.rejectValue("brandName", "",
                    "English letters only, digits, space, symbols .%#@&$ (3-30 characters)");
        }
        // countryName
        String countryNamePattern = "^[A-Z]{1}[a-z]{2,29}$";
        if(!product.getCountryName().matches(countryNamePattern)) {
            errors.rejectValue("countryName", "",
                    "English letters only, first letter is capitalized, other - lowercase (3-30 characters)");
        }
        // measurementName
        String measurementNamePattern = "^[A-Za-z]{1,4}$";
        if(!product.getMeasurementName().matches(measurementNamePattern)) {
            errors.rejectValue("measurementName", "",
                    "English letters only, (1-4 characters)");
        }
    }
}
