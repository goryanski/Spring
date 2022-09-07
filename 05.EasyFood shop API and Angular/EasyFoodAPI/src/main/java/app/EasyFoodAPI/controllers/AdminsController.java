package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.EditProductDTO;
import app.EasyFoodAPI.dto.responses.ProductLinkedDataResponseDTO;
import app.EasyFoodAPI.services.AdminsService;
import app.EasyFoodAPI.util.MessageResponse;
import app.EasyFoodAPI.util.exceptions.MakeOrderException;
import app.EasyFoodAPI.util.exceptions.ProductValidationException;
import app.EasyFoodAPI.util.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static app.EasyFoodAPI.util.ErrorsUtil.returnProductValidationErrorsToClient;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/admins")
public class AdminsController {
    private final AdminsService adminsService;
    private final ProductValidator productValidator;

    @Autowired
    public AdminsController(AdminsService adminsService, ProductValidator productValidator) {
        this.adminsService = adminsService;
        this.productValidator = productValidator;
    }


    @GetMapping("/edit/{productId}")
    public EditProductDTO getProductToEdit(@PathVariable("productId") int id) {
        return adminsService.getProductToEdit(id);
    }

    @GetMapping("/productLinkedData")
    public ProductLinkedDataResponseDTO getProductLinkedData() {
        return adminsService.getProductLinkedData();
    }



    @PutMapping("/editProduct")
    public MessageResponse editProduct(@RequestBody @Valid EditProductDTO productDTO,
                                        BindingResult bindingResult) {
        return addOrEditProductResponse(productDTO, bindingResult);
    }

    @PostMapping("/addProduct")
    public MessageResponse addProduct(@RequestBody @Valid EditProductDTO productDTO,
                                       BindingResult bindingResult) {
        return addOrEditProductResponse(productDTO, bindingResult);
    }


    private MessageResponse addOrEditProductResponse(EditProductDTO productDTO,
                                                    BindingResult bindingResult) {
        productValidator.validate(productDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            returnProductValidationErrorsToClient(bindingResult);
        }

        adminsService.addOrEditProduct(productDTO);
        return new MessageResponse(
                "OK",
                System.currentTimeMillis()
        );
    }


    @ExceptionHandler
    private MessageResponse handleException(ProductValidationException e) {
        // message from exception put to response and send to client
        return new MessageResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }

    @PutMapping("/blockUser")
    public MessageResponse blockUser(@RequestBody Integer userId) {
        return new MessageResponse(
                adminsService.blockUser(userId),
                System.currentTimeMillis()
        );
    }

    @PutMapping("/setUserAsAdmin")
    public MessageResponse setUserAsAdmin(@RequestBody Integer userId) {
        return new MessageResponse(
                adminsService.setUserAsAdmin(userId),
                System.currentTimeMillis()
        );
    }


// test: get authenticated user (role=admin) from spring security context (to check spring security configuration for authentication and authorization)
//    @GetMapping("/get-info")
//    public Map<String, String> addProduct() {
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
