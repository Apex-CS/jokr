package apex.ingagers.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

import apex.ingagers.ecommerce.model.SubCategories;

public interface SubCategoriesRepository extends JpaRepository<SubCategories, Integer> {
    SubCategories findByName(String name);
    @Query(value = "SELECT * FROM sub_categories WHERE  is_active = 1", nativeQuery = true)
    List<SubCategories> findAllCategories();

    @Query(value = "SELECT * FROM sub_categories WHERE  is_active = 1 and id=?1", nativeQuery = true)
    List<SubCategories> findSubCategoriesById(Integer id);

    @Query(value = "SELECT * FROM sub_categories WHERE  is_active = 1 and id_category=?1", nativeQuery = true)
    List<SubCategories> findSubCategoriesByIdCategory(Integer id);
}
