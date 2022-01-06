package apex.ingagers.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.UserRepository;
import apex.ingagers.ecommerce.security.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
@RequestMapping("/api/v1/public/")
public class AuthController {

  private final UserRepository userRepository;

  AuthController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  private JWTUtil jwtUtil;

  @PostMapping("/Auth") // Map ONLY POST Requests
  public ResponseEntity<?> login(@RequestBody Users user) {
    try {
      List<Users> list = userRepository.VerifyCredentials(user.getEmail());

      if (list.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      Users userdb = list.get(0);
      String passwordHashed = userdb.getPassword();
      Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

      if (argon2.verify(passwordHashed, user.getPassword())) {

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtil.create(userdb))
            .body(userdb);
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

  }

}
