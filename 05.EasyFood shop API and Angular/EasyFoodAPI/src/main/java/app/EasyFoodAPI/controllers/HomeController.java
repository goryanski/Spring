package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.responses.HomePageResponse;
import app.EasyFoodAPI.services.CategoriesService;
import app.EasyFoodAPI.services.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoriesService categoriesService;
    private final MapperService mapper;

    @Autowired
    public HomeController(CategoriesService categoriesService, MapperService mapper) {
        this.categoriesService = categoriesService;
        this.mapper = mapper;
    }

    @GetMapping
    public HomePageResponse getHomePage() {
        return new HomePageResponse(categoriesService.getAll().stream()
                .map(mapper::convertCategory)
                .collect(Collectors.toList()));
    }
}
