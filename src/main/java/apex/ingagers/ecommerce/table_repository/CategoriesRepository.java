package apex.ingagers.ecommerce.table_repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.tables.Categories;

public interface CategoriesRepository extends CrudRepository<Categories, Integer> {}
