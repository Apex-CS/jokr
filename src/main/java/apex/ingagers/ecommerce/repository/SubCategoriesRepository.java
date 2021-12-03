package apex.ingagers.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.SubCategories;

public interface SubCategoriesRepository extends JpaRepository<SubCategories, Integer> {
    SubCategories findByName(String name);
}
