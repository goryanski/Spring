package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.models.PersonTest;
import app.EasyFoodAPI.security.JWTUtil;
import app.EasyFoodAPI.security.PersonDetails;
import app.EasyFoodAPI.services.AuthService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.RegistrationException;
import app.EasyFoodAPI.util.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static app.EasyFoodAPI.util.ErrorsUtil.returnRegistrationErrorsToClient;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/auth")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final PersonValidator personValidator;
    private final AuthService authService;

    @Autowired
    public AuthController(JWTUtil jwtUtil, PersonValidator personValidator, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.personValidator = personValidator;
        this.authService = authService;
    }

    @PostMapping("/registration")
    public MessageResponse registration(@RequestBody @Valid RegisterPersonDTO person,
                                            BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            returnRegistrationErrorsToClient(bindingResult);
        }
        authService.registerPerson(person);

        return new MessageResponse(
                "OK",
                System.currentTimeMillis()
        );
    }

    @ExceptionHandler
    private MessageResponse handleException(RegistrationException e) {
        // message from exception put to ErrorsResponse and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }



// generate and return token to client
//        String token = jwtUtil.generateToken(person.getUsername());
//        return Map.of("jwt-token", token);



    // when user is authenticated (test method)
    @GetMapping("/getUserInfo")
    public PersonTest showUserInfo() {
        // получаем доступ к объекту Authentication из джава потока
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // authentication.getPrincipal() returns interface UserDetails, but we need PersonDetails object to call method getPerson() on it
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        //System.out.println(personDetails.getPerson());

        return personDetails.getPerson();
    }
}
