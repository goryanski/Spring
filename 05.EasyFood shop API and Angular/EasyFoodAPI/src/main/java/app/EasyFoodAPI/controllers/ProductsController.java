package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.FullProductInfoDTO;
import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.dto.requestObjects.ProductsByCategoryRequestObjectDTO;
import app.EasyFoodAPI.services.MapperService;
import app.EasyFoodAPI.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/products")
public class ProductsController {
    private final MapperService mapper;
    private final ProductsService productsService;

    @Autowired
    public ProductsController(MapperService mapper, ProductsService productsService) {
        this.mapper = mapper;
        this.productsService = productsService;
    }

    @PostMapping("/byCategoryId")
    public List<ShortProductInfoDTO> getProductsByCategoryId (
                @RequestBody ProductsByCategoryRequestObjectDTO requestObject) {
        return productsService.getProductsByCategoryId (
                        requestObject.getCategoryId(),
                        requestObject.getSkip(),
                        requestObject.getLimit())
                .stream()
                .map(mapper::convertProduct)
                .collect(Collectors.toList());
    }

    @GetMapping("/count/{categoryId}")
    public long getAllProductsCountByCategoryId(@PathVariable("categoryId") int id) {
        return productsService.getAllProductsCountByCategoryId(id);
    }

    @GetMapping("/{productId}")
    public FullProductInfoDTO getFullInfoProductById(@PathVariable("productId") int id) {
        return productsService.getProductById(id);
    }
}