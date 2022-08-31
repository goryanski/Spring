package app.EasyFoodAPI.util.validators;
import app.EasyFoodAPI.dto.requests.MakeOrderRequestDTO;
import app.EasyFoodAPI.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public OrderValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MakeOrderRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MakeOrderRequestDTO order = (MakeOrderRequestDTO) target;

        // check if user exists
        if(peopleService.getPersonById(order.getUserId()).isEmpty()) {
            errors.rejectValue("userId", "", "User not found");
        }

        // check phone number
        String pattern = "^[0]{1}[1-9]{1}[0-9]{8}$";
        if(!order.getPhoneNumber().matches(pattern)) {
            errors.rejectValue("phoneNumber", "",
                    "phone number must be like: 0934094350");
        }
    }
}
