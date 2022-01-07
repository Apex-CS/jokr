package apex.ingagers.ecommerce.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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

    SubCategoriesController(SubCategoriesRepository subCategoriesRepository) {
        this.subCategoriesRepository = subCategoriesRepository;
    }
    @PreAuthorize("hasAuthority ('Shopper')")
    @GetMapping("/subcategories")
    public List<SubCategories> getAllSubcategories() {
        return subCategoriesRepository.findAll();
    }

    @GetMapping("/subcategories/{id}")
    public List<SubCategories> getSubCategoriesById(@PathVariable("id") Integer id) {
        return subCategoriesRepository.findSubCategoriesById(id);
    }

    @GetMapping("/subcategories/categories/{id_categories}")
    public List<SubCategories> getSubCategoriesByIdCategories(@PathVariable("id_categories") Integer id_categories) {
        return subCategoriesRepository.findSubCategoriesByIdCategory(id_categories);
    }
}
