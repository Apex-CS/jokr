package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import apex.ingagers.ecommerce.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    @Query(value = "SELECT * FROM products WHERE  is_active = 1", nativeQuery = true)
    List<Products> findAllProducts();

    @Query(value = "SELECT * FROM products WHERE  is_active = 1 and id=?1", nativeQuery = true)
    Optional<Products> findProductsById(Integer id);
}
