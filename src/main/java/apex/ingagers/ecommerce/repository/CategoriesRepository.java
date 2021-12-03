package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String name);
}



