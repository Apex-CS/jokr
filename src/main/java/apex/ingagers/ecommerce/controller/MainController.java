package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import apex.ingagers.ecommerce.model.User;
import apex.ingagers.ecommerce.repository.UserRepository;

@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /demo (after Application path)
public class MainController {
  @Autowired // This means to get the bean called userRepository
  private UserRepository userRepository;


  @PostMapping(path="/add") // Map ONLY POST Requests
  //public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
  public @ResponseBody String addNewUser (@RequestBody Map<String,Object> values) {
    
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

  @GetMapping(path="/show")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }



  @GetMapping(path="/test")
  public @ResponseBody String test() {
    return "This is a test";
  }
}
