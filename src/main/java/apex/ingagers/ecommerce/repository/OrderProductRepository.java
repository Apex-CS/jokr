package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {}