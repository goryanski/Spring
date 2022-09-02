package app.EasyFoodAPI.util.validators;

import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.dto.requests.UpdatePersonRequestDTO;
import app.EasyFoodAPI.services.AccountsService;
import app.EasyFoodAPI.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class UpdatePersonValidator implements Validator {
    private final PeopleService peopleService;
    private final AccountsService accountsService;

    @Autowired
    public UpdatePersonValidator(PeopleService peopleService, AccountsService accountsService) {
        this.peopleService = peopleService;
        this.accountsService = accountsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePersonRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdatePersonRequestDTO person = (UpdatePersonRequestDTO) target;

        // check all fields for emptiness before validation
        // unique fields validation
        if(!person.getEmail().equals("")) {
            if(peopleService.getPersonByEmail(person.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "Person with this email already exists");
            }
        }
        if(!person.getUsername().equals("")) {
            // we can't use @Size annotation in the model for string length validation, so do it manually
            if(person.getUsername().length() < 2 || person.getUsername().length() > 36) {
                errors.rejectValue("username", "", "Username must be between 2 and 36 symbols");
            } else {
                if(accountsService.getAccountByUsername(person.getUsername()).isPresent()) {
                    errors.rejectValue("username", "", "Person with this username already exists");
                }
            }
        }


        if(!person.getFullName().equals("")) {
            if(person.getFullName().length() < 2 || person.getFullName().length() > 74) {
                errors.rejectValue("fullName", "", "FullName must be between 2 and 74 symbols");
            }
        }
        if(!person.getPassword().equals("")) {
            if(person.getPassword().length() < 4 || person.getPassword().length() > 36) {
                errors.rejectValue("password", "", "Password must be between 4 and 36 symbols");
            }
        }


        // check if user exists
        if(person.getId() != null) {
            if(peopleService.getPersonById(person.getId()).isEmpty()) {
                errors.rejectValue("id", "", "Person not found");
            }
        } else {
            errors.rejectValue("id", "", "Invalid id");
        }


        // dateOfBirth validation
        if(!person.getDateOfBirth().equals("")) {
            Date date;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                date = dateFormat.parse(person.getDateOfBirth());

                // check date > 1900 && date < now
                Date minDate = new GregorianCalendar(1900, Calendar.JANUARY , 1).getTime();
                Date maxDate = Calendar.getInstance().getTime(); // current

                if(date.before(minDate) || date.after(maxDate)) {
                    errors.rejectValue("dateOfBirth", "",
                            "Your date of birth cannot be earlier then 1900 and greater than the current date");
                }
            } catch (ParseException e) {
                errors.rejectValue("dateOfBirth", "",
                        "Your date of birth must be valid and in format (dd/mm/yyyy)");
            }
        }
    }
}
