package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.UserTransaction;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController // This means that this class is a Controller
@RequestMapping("/api/v1")
public class UsersController {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  UsersController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @PostMapping("/Users") // Map ONLY POST Requests
  // public @ResponseBody String addNewUser (@RequestParam String name,
  // @RequestParam String email) {
  Users addNewUser(@RequestBody Map<String, Object> values) {

    String role = String.valueOf(values.get("role"));
    String email = String.valueOf(values.get("email"));
    String password = String.valueOf(values.get("password"));
    String name = String.valueOf(values.get("name"));
    String lastName = String.valueOf(values.get("lastName"));
    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    Roles rol;
    rol = rolesRepository.findByRolename(role);

    Users n = new Users();
    n.setEmail(email);
    n.setPassword(password);
    n.setName(name);
    n.setLastName(lastName);
    n.setRole(rol);
    n.setUpdated_at(null);
    n.setCreated_at(sqlTimestamp);

    return userRepository.save(n);
  }

  @GetMapping("/Users")
  public List<Users> getAllUsers() {
    // This returns a JSON or XML with the Users
    return userRepository.findAllUsers();
  }


  @GetMapping("/Users/{id}")
  public Optional<Users> getUserbyId(@PathVariable("id") Integer id)
    {
        return userRepository.findUserById(id); 
  }

  @DeleteMapping("/Users/{id}")
  public boolean eliminar(@PathVariable("id") Integer id) {

    Optional<Users> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      Users Users = optionalUser.get();
      if (Users.getIs_active() == true) {

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        Users.setIs_active(false);
        Users.setDelete_at(sqlTimestamp);
        userRepository.save(Users);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @PutMapping("/Users/{id}")
  public Users update(@PathVariable("id") Integer id, @RequestBody Map<String, Object> values) {

    Optional<Users> optionaluser = userRepository.findById(id);

    if (optionaluser.isPresent()) {
      Users Users = optionaluser.get();
      String role = String.valueOf(values.get("role"));
      String email = String.valueOf(values.get("email"));
      String password = String.valueOf(values.get("password"));
      String name = String.valueOf(values.get("name"));
      String lastName = String.valueOf(values.get("lastName"));

      Roles rol;
      rol = rolesRepository.findByRolename(role);

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      Users.setEmail(email);
      Users.setPassword(password);
      Users.setName(name);
      Users.setLastName(lastName);
      Users.setRole(rol);
      Users.setUpdated_at(sqlTimestamp);

      userRepository.save(Users);

      return Users;
    }
    return null;
  }
}
