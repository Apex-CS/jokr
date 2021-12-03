package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
