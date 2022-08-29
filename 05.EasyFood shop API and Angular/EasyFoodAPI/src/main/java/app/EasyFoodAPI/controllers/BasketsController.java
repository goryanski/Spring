package app.EasyFoodAPI.controllers;

import app.EasyFoodAPI.dto.requestObjects.BasketProductRequestDTO;
import app.EasyFoodAPI.dto.requestObjects.RemoveBasketProductRequestDTO;
import app.EasyFoodAPI.services.BasketsService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.AddProductToBasketException;
import app.EasyFoodAPI.util.validators.BasketProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static app.EasyFoodAPI.util.ErrorsUtil.returnAddProductToBasketErrorsToClient;

@RestController
@RequestMapping("/api/easyFood/basket")
@CrossOrigin
public class BasketsController {
    private final BasketsService basketsService;
    private final BasketProductValidator basketProductValidator;

    @Autowired
    public BasketsController(BasketsService basketsService,
                             BasketProductValidator basketProductValidator) {
        this.basketsService = basketsService;
        this.basketProductValidator = basketProductValidator;
    }

    @PostMapping("/addToBasket")
    public MessageResponse addProductToBasket(@RequestBody @Valid BasketProductRequestDTO basketProductDTO,
                                              BindingResult bindingResult) {
        basketProductValidator.validate(basketProductDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            returnAddProductToBasketErrorsToClient(bindingResult);
        }

        String response = basketsService.addProductToBasket(basketProductDTO);
        return new MessageResponse(
                response,
                System.currentTimeMillis()
        );
    }

    @PostMapping("/removeFromBasket")
    public MessageResponse removeProductFromBasket(@RequestBody RemoveBasketProductRequestDTO basketProductDTO) {
        String response = basketsService.removeProductFromBasket(basketProductDTO);
        return new MessageResponse(
                response,
                System.currentTimeMillis()
        );
    }

    @GetMapping("/countProductsInBasket/{userId}")
    public int getUserProductsCountInBasket(@PathVariable("userId") int id) {
        return basketsService.getUserProductsCountInBasket(id);
    }

    @ExceptionHandler
    private MessageResponse handleException(AddProductToBasketException e) {
        // message from exception put to ErrorsResponse and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }
}
