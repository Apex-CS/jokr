package apex.ingagers.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query(value = "SELECT * FROM orders WHERE  is_active = 1 and id_user=?1", nativeQuery = true)
    List<Orders> findOrdersByUserId(Integer id);
}
