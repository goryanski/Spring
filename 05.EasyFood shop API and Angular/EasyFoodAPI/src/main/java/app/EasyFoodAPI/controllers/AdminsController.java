package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.EditProductDTO;
import app.EasyFoodAPI.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/admins")
public class AdminsController {
    private final AdminsService adminsService;

    @Autowired
    public AdminsController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }


    @GetMapping("/edit/{productId}")
    public EditProductDTO getProductToEdit(@PathVariable("productId") int id) {
        return adminsService.getProductToEdit(id);
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
