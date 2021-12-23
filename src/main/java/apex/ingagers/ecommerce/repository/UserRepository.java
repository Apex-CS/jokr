package apex.ingagers.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT * FROM users WHERE  is_active = 1", nativeQuery = true)
   List<Users> findAllUsers();

   @Query(value = "SELECT * FROM users WHERE  is_active = 1 and id=?1", nativeQuery = true)
   Optional<Users> findUserById(Integer id);

<<<<<<< HEAD
   Optional<Users> findByName(String name);
=======
   @Query(value = "SELECT * FROM users WHERE  is_active = 1 and email=?1 and password=?2", nativeQuery = true)
   List<Users> VerifyCredentials(String email, String password );

   Users findByName(String name);
>>>>>>> 12bd6f0 (Add basic structure og login)

}
