package apex.ingagers.ecommerce.table_repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.tables.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {}
