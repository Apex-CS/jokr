package apex.ingagers.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT * FROM users WHERE  is_active = 1", nativeQuery = true)
   List<Users> findAllUsers();

   @Query(value = "SELECT * FROM users WHERE  is_active = 1 and id=?1", nativeQuery = true)
   List<Users> findUserById(Integer id);

   @Query(value = "SELECT * FROM users WHERE  is_active = 1  AND email=?1 AND password=?2", nativeQuery = true)
   List<Users> VerifyCredentials(String email, String password );

   @Query(value = "SELECT * FROM users WHERE  is_active = 1  AND email=?1 ", nativeQuery = true)
   List<Users> VerifyCredentials(String email );

   Users findByName(String name);


}
