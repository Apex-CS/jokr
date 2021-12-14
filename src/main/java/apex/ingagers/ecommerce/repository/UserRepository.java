package apex.ingagers.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import apex.ingagers.ecommerce.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT * FROM users WHERE  is_active = 1", nativeQuery = true)
   List<Users> findAllUsers();

   @Query(value = "SELECT * FROM users WHERE  is_active = 1 and id=?1", nativeQuery = true)
   Optional<Users> findUserById(Integer id);

   Users findByName(String name);

}
