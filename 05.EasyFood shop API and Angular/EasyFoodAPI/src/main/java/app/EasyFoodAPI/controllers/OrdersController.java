package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.OrderDTO;
import app.EasyFoodAPI.dto.OrderedProductDTO;
import app.EasyFoodAPI.dto.requests.MakeOrderRequestDTO;
import app.EasyFoodAPI.services.OrdersService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.AddProductToBasketException;
import app.EasyFoodAPI.util.exceptions.MakeOrderException;
import app.EasyFoodAPI.util.validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static app.EasyFoodAPI.util.ErrorsUtil.returnMakeOrderErrorsToClient;


@RestController
@RequestMapping("/api/easyFood/orders")
@CrossOrigin
public class OrdersController {
    private final OrdersService ordersService;
    private final OrderValidator orderValidator;

    @Autowired
    public OrdersController(OrdersService ordersService, OrderValidator orderValidator) {
        this.ordersService = ordersService;
        this.orderValidator = orderValidator;
    }


    @PostMapping("/makeOrder")
    public MessageResponse makeOrder(@RequestBody @Valid MakeOrderRequestDTO orderDTO,
                                     BindingResult bindingResult) {
        orderValidator.validate(orderDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            returnMakeOrderErrorsToClient(bindingResult);
        }

        ordersService.makeOrder(orderDTO);
        return new MessageResponse(
                "OK",
                System.currentTimeMillis()
        );
    }

    @GetMapping("/userOrders/{userId}")
    public List<OrderDTO> getUserOrders(@PathVariable("userId") int id) {
        return ordersService.getUserOrders(id);
    }

    @GetMapping("/orderedProducts/{orderId}")
    public List<OrderedProductDTO> getOrderProducts(@PathVariable("orderId") int id) {
        return ordersService.getOrderProducts(id);
    }


    @ExceptionHandler
    private MessageResponse handleException(MakeOrderException e) {
        // message from exception put to response and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }
}
