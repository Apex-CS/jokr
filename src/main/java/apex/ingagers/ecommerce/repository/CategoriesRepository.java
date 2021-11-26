package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.model.Categories;

public interface CategoriesRepository extends CrudRepository<Categories, Integer> {
    Categories findByName(String name);
}



