package apex.ingagers.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import apex.ingagers.ecommerce.model.OrderStatuses;

public interface OrderStatusesRepository extends JpaRepository<OrderStatuses, Integer> {
    OrderStatuses findByStatusname(String status_name);
}
