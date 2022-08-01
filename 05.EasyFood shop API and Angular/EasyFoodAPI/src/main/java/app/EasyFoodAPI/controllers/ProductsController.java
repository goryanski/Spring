package app.EasyFoodAPI.controllers;

import app.EasyFoodAPI.dto.ShortProductInfoDTO;
import app.EasyFoodAPI.services.MapperService;
import app.EasyFoodAPI.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/easyFood/products")
@CrossOrigin
public class ProductsController {
    private final MapperService mapper;
    private final ProductsService productsService;

    @Autowired
    public ProductsController(MapperService mapper, ProductsService productsService) {
        this.mapper = mapper;
        this.productsService = productsService;
    }

    @GetMapping("/{categoryId}")
    public List<ShortProductInfoDTO> getProductsByCategoryId(@PathVariable("categoryId") int id) {
        return productsService.getProductsByCategoryId(id).stream()
                .map(mapper::convertProduct)
                .collect(Collectors.toList());
    }
}
