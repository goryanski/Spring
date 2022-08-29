package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.FullProductInfoDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByCategoryRequestDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByNameRequestDTO;
import app.EasyFoodAPI.services.GetProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/products")
public class GetProductsController {
    private final GetProductsService productsService;

    @Autowired
    public GetProductsController(GetProductsService getProductsService) {
        this.productsService = getProductsService;
    }

    @PostMapping("/byCategoryId")
    public ResponseEntity<Map<String, Object>> getProductsByCategoryId (
                @RequestBody ProductsByCategoryRequestDTO params) {
        Map<String, Object> response = productsService.getProductsByCategoryId(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public FullProductInfoDTO getFullInfoProductById(@PathVariable("productId") int id) {
        return productsService.getFullProductById(id);
    }

    @GetMapping("/similar/{categoryId}/{count}")
    public List<ShortProductInfoDTO> getSimilarProducts(@PathVariable("categoryId") int categoryId,
                                                        @PathVariable("count") int productsCount) {
        return productsService.getSimilarProducts(categoryId, productsCount);
    }

    @PostMapping("/byName")
    public ResponseEntity<Map<String, Object>> getProductsBySubstringOfName (
            @RequestBody ProductsByNameRequestDTO params) {
        Map<String, Object> response = productsService.getProductsBySubstringOfName(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}