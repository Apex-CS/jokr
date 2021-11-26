package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
