package apex.ingagers.ecommerce.table_repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.tables.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
