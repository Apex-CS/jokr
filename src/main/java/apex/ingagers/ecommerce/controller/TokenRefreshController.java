package apex.ingagers.ecommerce.controller;

import java.util.List;
import java.util.Map;
import java.util.Date;

import apex.ingagers.ecommerce.model.Users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.repository.UserRepository;
import apex.ingagers.ecommerce.security.JWTUtil;

@RestController
@RequestMapping("/api/v1/")
public class TokenRefreshController {

    private final UserRepository userRepository;

    TokenRefreshController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/refreshJwt") // Map ONLY POST Requests
    public ResponseEntity<?> login(@RequestBody Map<String, Object> token) {

        String onlyToken = String.valueOf(token.get("token")).split(" ")[1].trim();
        String idUser = jwtUtil.getUserId(onlyToken);
        Date datoexpired = jwtUtil.getExpirationDate(onlyToken);

        // long nowMillis = System.currentTimeMillis();
        // Date now = new Date(nowMillis);
        long actualTime = System.currentTimeMillis();
        long tokenTime = datoexpired.getTime();

        if ((tokenTime - actualTime) < 300000) {
            List<Users> optionalUser = userRepository.VerifyCredentials(idUser);

            if (!optionalUser.isEmpty()) {
                Users Users = optionalUser.get(0);
                if (Users.getIs_active() == true) {
                    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtil.create(Users)).body("");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " +
        // jwtUtil.create(userdb)).body("Bearer " + jwtUtil.create(userdb));

        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
