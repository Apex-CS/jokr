package apex.ingagers.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

  private final UserRepository userRepository;

  AuthController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/Auth") // Map ONLY POST Requests
  public boolean login(@RequestBody Users user) {

    List<Users> list = userRepository.VerifyCredentials(user.getEmail());

    if (list.isEmpty()) {
      return false;
    }

    String passwordHashed = list.get(0).getPassword();
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    return argon2.verify(passwordHashed, user.getPassword());
  }

}
