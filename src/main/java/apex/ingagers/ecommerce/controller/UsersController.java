package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@RestController // This means that this class is a Controller
@RequestMapping("/api/v1")
public class UsersController {

  @Value("${stripe.apikey}")
  String stripeKey;

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  UsersController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @PostMapping("/users") // Map ONLY POST Requests
  HttpStatus addNewUser(@RequestBody Users user) {

    List <Users> list = userRepository.VerifyCredentials(user.getEmail());
    if(!list.isEmpty()){
      return HttpStatus.NOT_ACCEPTABLE;
    }

    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String hash = argon2.hash(1, 1024, 1, user.getPassword());
     


    String role = user.getRoleName();

    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    Roles rol;
    rol = rolesRepository.findByRolename(role);

    Users n = new Users();
    n = user;
    n.setRole(rol);
    n.setCreated_at(sqlTimestamp);
    n.setPassword(hash);

    if (userRepository.save(n) != null) {
      return HttpStatus.OK;
    } else {
      return HttpStatus.BAD_REQUEST;
    }

  }

  @PostMapping("/users/image") // Map ONLY POST Requests
  public Map<String, String> addNewUserImage(@RequestPart MultipartFile file) throws IOException {

    HashMap<String, String> map = new HashMap<>();
    if (file == null || file.isEmpty()) {
      map.put("id", "");
      map.put("url", "");
      return map;
    }

    // File Validations
    if (file == null || file.isEmpty()) {
      // If the file(image) is empty
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Please upload an image");
    }

    List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
    String fileContentType = file.getContentType();

    if (!contentTypes.contains(fileContentType)) {
      // the is not correct extension
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, "Please upload an image with the correct extension(JPG,JPEG,PNG)");
    }

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "dpakhjsmh", // "ddlqf2qer",
        "api_key", "679976426528739", // "941731261856649",
        "api_secret", "a4vooY53qGsobBvJAU4i4Jf5__A", // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
        "secure", true));
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "Jokr/usersPhoto/"));

    String photoUrl = String.valueOf(uploadResult.get("url"));
    String photoPublicId = String.valueOf(uploadResult.get("public_id"));

    String[] parts = photoPublicId.split("/");
    String photoId = parts[2];

    map.put("id", photoId);
    map.put("url", photoUrl);

    System.out.println("Holaa");

    return map;

  }

  @DeleteMapping("/users/image/{id_image}")
  public Map<String, String> deleteImage(@PathVariable("id_image") String id_image) throws IOException {

    String idImage = "Jokr/usersPhoto/" + id_image;
    HashMap<String, String> map = new HashMap<>();

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "dpakhjsmh", // "ddlqf2qer",
        "api_key", "679976426528739", // "941731261856649",
        "api_secret", "a4vooY53qGsobBvJAU4i4Jf5__A", // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
        "secure", true));

    cloudinary.uploader().destroy(idImage, ObjectUtils.asMap("overwrite", "true", "public_id", idImage));

    map.put("ID", String.valueOf(idImage));
    map.put("RESPONSE", String.valueOf(idImage));

    return map;

  }

  @GetMapping("/users")
  public List<Users> getAllUsers() {
    // This returns a JSON or XML with the Users
    return userRepository.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public List<Users> getUserbyId(@PathVariable("id") Integer id) {
    return userRepository.findUserById(id);
  }

  @DeleteMapping("/users/{id}")
  public boolean deleteUser(@PathVariable("id") Integer id) {

    List<Users> optionalUser = userRepository.findUserById(id);

    if (!optionalUser.isEmpty()) {
      Users Users = optionalUser.get(0);
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

  @PutMapping("/users/{id}")
  public Users updateUser(@PathVariable("id") Integer id, @RequestBody Users user) {

    List<Users> optionaluser = userRepository.findUserById(id);

    if (!optionaluser.isEmpty()) {

      List <Users> list = userRepository.VerifyCredentials(user.getEmail());
      if(!list.isEmpty()){
        return null;
      }

      Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
      String hash = argon2.hash(1, 1024, 1, user.getPassword());


      Users Users = optionaluser.get(0);
      String role = user.getRoleName();
      String email = user.getEmail();
      String password = hash;
      String name = user.getName();
      String lastName = user.getLastName();

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
