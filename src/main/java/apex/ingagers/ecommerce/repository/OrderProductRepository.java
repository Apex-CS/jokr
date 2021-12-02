package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.model.OrderProduct;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Integer> {}