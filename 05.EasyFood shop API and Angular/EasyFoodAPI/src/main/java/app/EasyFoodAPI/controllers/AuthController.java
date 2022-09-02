package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.auth.JwtResponseDTO;
import app.EasyFoodAPI.dto.auth.LoginRequestDTO;
import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.security.JwtTokenProvider;
import app.EasyFoodAPI.services.AccountsService;
import app.EasyFoodAPI.services.AuthService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.AuthException;
import app.EasyFoodAPI.util.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static app.EasyFoodAPI.util.ErrorsUtil.returnRegistrationErrorsToClient;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(PersonValidator personValidator,
                          AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.personValidator = personValidator;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody LoginRequestDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            // Incorrect username or password. find out what's incorrect exactly and return error:
            return authService.getJwtErrorResponse(authenticationDTO.getUsername());
        }

        String token = jwtTokenProvider.generateToken(authenticationDTO.getUsername());
        return authService.getJwtSuccessResponse(authenticationDTO.getUsername(), token);
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
    private MessageResponse handleException(AuthException e) {
        // message from exception put to response and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }
}
