package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Categories;
import apex.ingagers.ecommerce.repository.CategoriesRepository;

@RestController
@RequestMapping("/api/v1")
public class CategoriesController {
    
    private final CategoriesRepository categoriesRepository;

    CategoriesController(CategoriesRepository categoriesRepository){
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping("/categories")
    public List<Categories> getAllCategories(){
        return categoriesRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Optional<Categories> getCategoriesById(@PathVariable("id") Integer id){
        return categoriesRepository.findCategoriesById(id);
    }

}
