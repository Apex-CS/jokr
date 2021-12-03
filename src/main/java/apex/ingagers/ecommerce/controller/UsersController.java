package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.User;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController // This means that this class is a Controller

public class UsersController {
  
  private final UserRepository userRepository;
  UsersController(UserRepository userRepository){
    this.userRepository = userRepository;
  }


  @PostMapping("/user") // Map ONLY POST Requests
  //public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
  public String addNewUser (@RequestBody Map<String,Object> values) {
    
    String email = String.valueOf(values.get("email"));
    String password = String.valueOf(values.get("password"));
    String name = String.valueOf(values.get("name"));
    String lastName = String.valueOf(values.get("lastName"));
    int is_active = Integer.parseInt(String.valueOf(values.get("is_active")));

    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    User n = new User();
    n.setEmail(email);
    n.setPassword(password);
    n.setName(name);
    n.setLastName(lastName);
    n.setUpdated_at(sqlTimestamp);
    n.setIs_active(is_active);
    n.setCreated_at(sqlTimestamp);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping("/user")
  public  Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }


  @GetMapping("/test")
  public String test() {
    return "This is a test";
  }
}
