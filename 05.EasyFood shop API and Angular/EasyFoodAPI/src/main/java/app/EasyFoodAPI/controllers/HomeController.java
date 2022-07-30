package app.EasyFoodAPI.controllers;
import app.EasyFoodAPI.dto.CategoryDTO;
import app.EasyFoodAPI.services.CategoriesService;
import app.EasyFoodAPI.services.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/easyFood/home")
@CrossOrigin
public class HomeController {
    private final CategoriesService categoriesService;
    private final MapperService mapper;

    @Autowired
    public HomeController(CategoriesService categoriesService, MapperService mapper) {
        this.categoriesService = categoriesService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CategoryDTO> getHomePageCategories() {
        return categoriesService.getAll().stream()
                .map(mapper::convertCategory)
                .collect(Collectors.toList());
    }
}
