package apex.ingagers.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserRepository userRepository;
  
    AuthController(UserRepository userRepository) {
      this.userRepository = userRepository;
    }

    @PostMapping("/Auth") // Map ONLY POST Requests
    public boolean  login (@RequestBody Users user) {
        List <Users> lista = userRepository.VerifyCredentials(user.getEmail(), user.getPassword());
        return !lista.isEmpty();
    }
    
}
