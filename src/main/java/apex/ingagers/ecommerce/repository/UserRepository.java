package apex.ingagers.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM products WHERE  is_active = 1", nativeQuery = true)
   List<User> findAllUsers();
}
