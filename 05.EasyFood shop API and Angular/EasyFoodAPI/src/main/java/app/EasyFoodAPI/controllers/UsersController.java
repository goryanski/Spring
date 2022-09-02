package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.PersonDTO;
import app.EasyFoodAPI.dto.requests.UpdatePersonRequestDTO;
import app.EasyFoodAPI.services.PeopleService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.AuthException;
import app.EasyFoodAPI.util.exceptions.UpdatePersonException;
import app.EasyFoodAPI.util.validators.UpdatePersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static app.EasyFoodAPI.util.ErrorsUtil.returnUpdatePersonErrorsToClient;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/users")
public class UsersController {
    private final PeopleService peopleService;
    private final UpdatePersonValidator personValidator;

    @Autowired
    public UsersController(PeopleService peopleService, UpdatePersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/{userId}")
    public PersonDTO getUserInfo(@PathVariable("userId") int id) {
        return peopleService.getUserInfo(id);
    }

    @PutMapping("/update")
    public MessageResponse editUserInfo(@RequestBody @Valid UpdatePersonRequestDTO person,
                                        BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            returnUpdatePersonErrorsToClient(bindingResult);
        }
        peopleService.editPersonInfo(person);

        return new MessageResponse(
                "OK",
                System.currentTimeMillis()
        );
    }


    @ExceptionHandler
    private MessageResponse handleException(UpdatePersonException e) {
        // message from exception put to response and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }





// test: get authenticated user (role=user) from spring security context (to check spring security configuration for authentication and authorization)
//    @GetMapping("/get-info")
//    public Map<String, String> getInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//
//        Map<String, String> response = new HashMap<>();
//        response.put("username", personDetails.getUsername());
//        response.put("role", personDetails.getAccount().getRole().getName());
//        response.put("email", personDetails.getAccount().getPerson().getEmail());
//
//        return response;
//    }
}