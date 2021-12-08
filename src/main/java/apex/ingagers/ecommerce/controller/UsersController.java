package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.User;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController // This means that this class is a Controller

public class UsersController {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  UsersController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @PostMapping("/user") // Map ONLY POST Requests
  // public @ResponseBody String addNewUser (@RequestParam String name,
  // @RequestParam String email) {
  User addNewUser(@RequestBody Map<String, Object> values) {

    String role = String.valueOf(values.get("role"));
    String email = String.valueOf(values.get("email"));
    String password = String.valueOf(values.get("password"));
    String name = String.valueOf(values.get("name"));
    String lastName = String.valueOf(values.get("lastName"));
    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    Roles rol;
    rol = rolesRepository.findByRolename(role);

    User n = new User();
    n.setEmail(email);
    n.setPassword(password);
    n.setName(name);
    n.setLastName(lastName);
    n.setRole(rol);
    n.setUpdated_at(null);
    n.setCreated_at(sqlTimestamp);

    return userRepository.save(n);
  }

  @GetMapping("/user")
  public List<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAllUsers();
  }

  @DeleteMapping("/user/{id}")
  public boolean eliminar(@PathVariable("id") Integer id) {

    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.getIs_active() == true) {

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        user.setIs_active(false);
        user.setDelete_at(sqlTimestamp);
        userRepository.save(user);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @PutMapping("/user/{id}")
  public User update(@PathVariable("id") Integer id, @RequestBody Map<String, Object> values) {

    Optional<User> optionaluser = userRepository.findById(id);

    if (optionaluser.isPresent()) {
      User user = optionaluser.get();
      String role = String.valueOf(values.get("role"));
      String email = String.valueOf(values.get("email"));
      String password = String.valueOf(values.get("password"));
      String name = String.valueOf(values.get("name"));
      String lastName = String.valueOf(values.get("lastName"));

      Roles rol;
      rol = rolesRepository.findByRolename(role);

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      user.setEmail(email);
      user.setPassword(password);
      user.setName(name);
      user.setLastName(lastName);
      user.setRole(rol);
      user.setUpdated_at(sqlTimestamp);

      userRepository.save(user);

      return user;
    }
    return null;
  }
}
