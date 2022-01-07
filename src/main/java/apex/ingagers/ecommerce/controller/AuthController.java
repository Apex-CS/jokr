package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
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

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
import apex.ingagers.ecommerce.security.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
@RequestMapping("/api/v1/public/")
public class AuthController {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  AuthController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @Autowired
  private JWTUtil jwtUtil;

  @PostMapping("/login") // Map ONLY POST Requests
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
            .body("Bearer " + jwtUtil.create(userdb));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

  }

  @PostMapping("/register")
  public ResponseEntity<?> createNewAccount(@RequestBody Users user){
    List<Users> list = userRepository.VerifyCredentials(user.getEmail());

    if (!list.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    // Create Encripted Password whit ARGON2
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String hash = argon2.hash(1, 1024, 1, user.getPassword());

    // Create the data time whith the real time
    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    // Find the rol in the db
    Roles rol = rolesRepository.findByRolename(user.getRoleName());

    Users newUser = new Users();
    newUser = user;
    newUser.setRole(rol);
    newUser.setCreated_at(sqlTimestamp);
    newUser.setPassword(hash);

    if (userRepository.save(newUser) != null) { 
      return ResponseEntity.ok().body(newUser);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
