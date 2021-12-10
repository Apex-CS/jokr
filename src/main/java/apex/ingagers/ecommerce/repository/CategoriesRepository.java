package apex.ingagers.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import apex.ingagers.ecommerce.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String name);

    @Query(value = "SELECT * FROM categories WHERE  is_active = 1", nativeQuery = true)
    List<Categories> findAllCategories();

    @Query(value = "SELECT * FROM Categories WHERE  is_active = 1 and id=?1", nativeQuery = true)
    Optional<Categories> findCategoriesById(Integer id);

}



