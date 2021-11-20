package apex.ingagers.ecommerce.table_repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.tables.Products;

public interface ProductsRepository extends CrudRepository<Products, Integer> {}
