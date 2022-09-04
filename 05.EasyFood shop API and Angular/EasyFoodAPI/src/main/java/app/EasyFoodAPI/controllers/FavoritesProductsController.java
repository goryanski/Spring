package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.dto.requests.FavoriteProductRequestDTO;
import app.EasyFoodAPI.dto.requests.FavoriteProductsByCategoryIdRequestDTO;
import app.EasyFoodAPI.services.FavoritesProductsService;
import app.EasyFoodAPI.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/favouritesProducts")
public class FavoritesProductsController {
    private final FavoritesProductsService favoritesProductsService;

    @Autowired
    public FavoritesProductsController(FavoritesProductsService favoritesProductsService) {
        this.favoritesProductsService = favoritesProductsService;
    }


    @PostMapping("/check")
    public boolean isProductFavorite (@RequestBody FavoriteProductRequestDTO params) {
        return favoritesProductsService.isProductFavorite(params);
    }

    @PostMapping("/add")
    public MessageResponse addProductToFavorites (@RequestBody FavoriteProductRequestDTO params) {
        String response = favoritesProductsService.addProductToFavorites(params);
        return new MessageResponse(
                response,
                System.currentTimeMillis()
        );
    }

    @PostMapping("/delete")
    public MessageResponse deleteProductFromFavorites (@RequestBody FavoriteProductRequestDTO params) {
        String response = favoritesProductsService.deleteProductFromFavorites(params);
        return new MessageResponse(
                response,
                System.currentTimeMillis()
        );
    }

    @GetMapping("/categories/{userId}")
    public List<CategoryDTO> getFavoritesProductsCategories(@PathVariable("userId") int id) {
        return favoritesProductsService.getFavoritesProductsCategories(id);
    }

    @PostMapping("/getProducts")
    public ResponseEntity<Map<String, Object>> getFavoriteProductsByCategoryId (
            @RequestBody FavoriteProductsByCategoryIdRequestDTO params) {
        Map<String, Object> response = favoritesProductsService.getFavoriteProductsByCategoryId(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
