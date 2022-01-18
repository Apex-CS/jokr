package apex.ingagers.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    
    @Query(value = "SELECT * FROM order_product WHERE orders_id=?1", nativeQuery = true)
    List<OrderProduct> findOrderProductsByOrderId(Integer id);
}