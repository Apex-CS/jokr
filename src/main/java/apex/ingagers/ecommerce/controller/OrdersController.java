package apex.ingagers.ecommerce.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.boot.Metadata;
import org.springframework.http.HttpStatus;
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

import apex.ingagers.ecommerce.model.OrderProduct;
import apex.ingagers.ecommerce.model.Orders;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.OrderProductRepository;
import apex.ingagers.ecommerce.repository.OrdersRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
import io.swagger.annotations.ApiOperation;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@RestController
@RequestMapping("/api/v1")

public class OrdersController {

    private final UserRepository userRepository;

    OrdersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/addresses") // Map ONLY POST Requests
    HttpStatus createOrder(@RequestBody Map<String, Object> values) {

      int id_user = Integer.parseInt(String.valueOf(values.get("id_user")));
        
      List<Users> optionalUser = userRepository.findUserById(id_user);
      if (!optionalUser.isEmpty()) {
          Users user = optionalUser.get(0);
      }
      
        String shippingAddress = String.valueOf(values.get("shippingAddress"));
        String billingAddress = 

        String street1 = String.valueOf(values.get("street1"));
        String street2 = String.valueOf(values.get("street2"));
        String colonia = String.valueOf(values.get("colonia"));
        String municipio = String.valueOf(values.get("municipio"));
        String state = String.valueOf(values.get("state"));
        String country = String.valueOf(values.get("country"));
        String postal_code = String.valueOf(values.get("postal_code"));
        String phone = String.valueOf(values.get("phone"));
        String client_name = String.valueOf(values.get("client_name"));
        int id_user = Integer.parseInt(String.valueOf(values.get("id_user")));
        // casteo de string

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        Orders order = new Orders();
      
        order.setCreated_at(sqlTimestamp);
        order.setUpdated_at(null);


        List<Users> optionalUser = userRepository.findUserById(id_user);
        if (!optionalUser.isEmpty()) {
            Users users = optionalUser.get(0);
            a.setUsers(users);
        }

        return addressesRepository.save(a);
    }

}