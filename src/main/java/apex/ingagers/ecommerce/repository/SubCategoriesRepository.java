package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import apex.ingagers.ecommerce.model.SubCategories;

public interface SubCategoriesRepository extends CrudRepository<SubCategories, Integer> {
    SubCategories findByName(String name);
}
