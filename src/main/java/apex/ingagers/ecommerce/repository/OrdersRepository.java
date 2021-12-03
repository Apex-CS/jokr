package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {}
