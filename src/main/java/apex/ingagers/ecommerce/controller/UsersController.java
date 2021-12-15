package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController // This means that this class is a Controller
@RequestMapping("/api/v1")
public class UsersController {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  UsersController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @PostMapping("/users") // Map ONLY POST Requests
  HttpStatus addNewUser(@RequestBody Users user, @RequestPart("file") MultipartFile file) throws IOException {

    String role = user.getRoleName();

    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    Roles rol;
    rol = rolesRepository.findByRolename(role);

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

    Users n = new Users();
    n = user;
    n.setRole(rol);
    n.setphotoUrl(photoUrl);
    n.setphotoPublicId(photoPublicId);
    n.setCreated_at(sqlTimestamp);

    System.out.println("Holaa");
    if (userRepository.save(n) != null) {
      return HttpStatus.OK;
    } else {
      return HttpStatus.BAD_REQUEST;
    }

  }

  @GetMapping("/users")
  public List<Users> getAllUsers() {
    // This returns a JSON or XML with the Users
    return userRepository.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public Optional<Users> getUserbyId(@PathVariable("id") Integer id) {
    return userRepository.findUserById(id);
  }

  @DeleteMapping("/users/{id}")
  public boolean deleteUser(@PathVariable("id") Integer id) {

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

  @PutMapping("/users/{id_User}")
  public Users updateUser(@PathVariable("idUser") Integer idUser, @RequestBody Users user,
      @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

    // convert JSON string to Map

    Optional<Users> optionaluser = userRepository.findById(idUser);

    if (optionaluser.isPresent()) {
      Users Users = optionaluser.get();
      String role = user.getRoleName();
      String email = user.getEmail();
      String password = user.getPassword();
      String name = user.getName();
      String lastName = user.getLastName();

      if (file != null) {
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

        Users currentUser = optionaluser.get();
        String photoPublicId = currentUser.getphotoPublicId();
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
            ObjectUtils.asMap("overwrite", "true", "public_id", photoPublicId));
      }
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
