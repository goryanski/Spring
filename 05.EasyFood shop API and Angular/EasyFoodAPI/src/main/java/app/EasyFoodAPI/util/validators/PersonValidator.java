package app.EasyFoodAPI.util.validators;
import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
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
public class PersonValidator implements Validator {
    private final PeopleService peopleService;
    private final AccountsService accountsService;

    @Autowired
    public PersonValidator(PeopleService peopleService, AccountsService accountsService) {
        this.peopleService = peopleService;
        this.accountsService = accountsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterPersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterPersonDTO person = (RegisterPersonDTO) target;

        // unique fields validation
        if(peopleService.getPersonByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Person with this email already exists");
        }
        if(accountsService.getAccountByUsername(person.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Person with this username already exists");
        }

        // dateOfBirth validation
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
                        "Date of birth cannot be earlier then 1900 and greater than the current date");
            }
        } catch (ParseException e) {
            errors.rejectValue("dateOfBirth", "",
                    "Date of birth must be valid and in format (dd/mm/yyyy)");
        }
    }
}
