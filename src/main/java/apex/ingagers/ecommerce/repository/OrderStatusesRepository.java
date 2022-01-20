package apex.ingagers.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import apex.ingagers.ecommerce.model.OrderStatuses;

public interface OrderStatusesRepository extends JpaRepository<OrderStatuses, Integer> {
    OrderStatuses findByStatusname(String status_name);

    @Query(value = "SELECT * FROM order_statuses WHERE  is_active = 1 and id=?1", nativeQuery = true)
    List<OrderStatuses> findStatusById(Integer id);
}
