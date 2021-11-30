package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    
}
