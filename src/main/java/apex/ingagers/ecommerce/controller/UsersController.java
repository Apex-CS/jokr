package apex.ingagers.ecommerce.controller;

import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
import org.springframework.http.MediaType;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


@RestController // This means that this class is a Controller
@RequestMapping("/api/v1")
public class UsersController {

  @Value("${cloudinary.credentials.cloud.name}")
  private String cloud_name;
  @Value("${cloudinary.credentials.api.key}")
  private String api_key;
  @Value("${cloudinary.credentials.api.secret}")
  private String api_secret;
  @Value("${cloudinary.credentials.secure}")
  private boolean secure;
  @Value("${stripe.apikey}")
  String stripeKey;

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  UsersController(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @GetMapping("/users")
  public List<Users> getAllUsers() {
    return userRepository.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public List<Users> getUserbyId(@PathVariable("id") Integer id) {
    return userRepository.findUserById(id);
  }

  @PostMapping("/users")
  HttpStatus addNewUser(@RequestBody Users user) {

    List<Users> list = userRepository.VerifyCredentials(user.getEmail());

    if (!list.isEmpty()) {
      return HttpStatus.NOT_ACCEPTABLE;
    }

    // Create Encripted Password whit ARGON2
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String hash = argon2.hash(1, 1024, 1, user.getPassword());

    // Create the data time whith the real time
    long now = System.currentTimeMillis();
    Timestamp sqlTimestamp = new Timestamp(now);

    // Find the rol in the db
    String role = user.getRoleName();
    Roles rol = rolesRepository.findByRolename(role);

    Users newUser = new Users();
    newUser = user;
    newUser.setRole(rol);
    newUser.setCreated_at(sqlTimestamp);
    newUser.setPassword(hash);

    if (userRepository.save(newUser) != null) {
      return HttpStatus.OK;
    } else {
      return HttpStatus.BAD_REQUEST;
    }

  }

  @PostMapping(value = "/users/image" , consumes  = { MediaType . MULTIPART_FORM_DATA_VALUE })
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
      "cloud_name", cloud_name, // "ddlqf2qer",
      "api_key", api_key, // "941731261856649",
      "api_secret", api_secret, // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
      "secure", secure));

    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "Jokr/usersPhoto/"));

    String photoUrl = String.valueOf(uploadResult.get("url"));
    String photoPublicId = String.valueOf(uploadResult.get("public_id"));

    String[] parts = photoPublicId.split("/");
    String photoId = parts[2];

    map.put("id", photoId);
    map.put("url", photoUrl);

    // System.out.println("Holaa");

    return map;

  }

  @DeleteMapping("/users/image/{id_image}")
  public Map<String, String> deleteImage(@PathVariable("id_image") String id_image) throws IOException {

    String idImage = "Jokr/usersPhoto/" + id_image;
    HashMap<String, String> map = new HashMap<>();

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", cloud_name, // "ddlqf2qer",
      "api_key", api_key, // "941731261856649",
      "api_secret", api_secret, // "Eq9Xyx0QkGqtsHO--0GRH8b4NaQ",
      "secure", secure));

    cloudinary.uploader().destroy(idImage, ObjectUtils.asMap("overwrite", "true", "public_id", idImage));

    map.put("ID", String.valueOf(idImage));
    map.put("RESPONSE", String.valueOf(idImage));

    return map;

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

      List<Users> list = userRepository.VerifyCredentials(user.getEmail());
      if (!list.isEmpty()) {
        return null;
      }

      Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
      String hash = argon2.hash(1, 1024, 1, user.getPassword());

      Roles rol = rolesRepository.findByRolename(user.getRoleName());

      long now = System.currentTimeMillis();
      Timestamp sqlTimestamp = new Timestamp(now);

      Users Users = optionaluser.get(0);
      Users.setEmail(user.getEmail());
      Users.setLastName(user.getLastName());
      Users.setName(user.getName());
      Users.setPassword(hash);
      Users.setphotoPublicId(user.getphotoPublicId());
      Users.setphotoUrl(user.getphotoUrl());
      Users.setRole(rol);
      Users.setUpdated_at(sqlTimestamp);

      return userRepository.save(Users);
    }
    return null;
  }
}
