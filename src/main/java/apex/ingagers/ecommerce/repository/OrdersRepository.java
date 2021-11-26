package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.model.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {}
