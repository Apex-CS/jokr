package apex.ingagers.ecommerce.controller;


import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;

@RestController
@RequestMapping("/api/v1")
public class SubCategoriesController {
    
    private final SubCategoriesRepository subCategoriesRepository;

    SubCategoriesController(SubCategoriesRepository subCategoriesRepository){
        this.subCategoriesRepository = subCategoriesRepository;
    }

    @GetMapping("/subcategories")
    public List<SubCategories> getAllSubcategories(){
        return subCategoriesRepository.findAll();
    }

    @GetMapping("/subcategories/{id}")
    public Optional<SubCategories> getSubCategoriesById(@PathVariable("id") Integer id){
        return subCategoriesRepository.findSubCategoriesById(id);
    }
}
