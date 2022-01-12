package apex.ingagers.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.UserRepository;


@RestController
@RequestMapping("/api/v1")
public class StripeController {

  @Value("${stripe.apikey}")
  String stripeKey;
  private final UserRepository userRepository;

  StripeController(UserRepository userRepository) {
    this.userRepository = userRepository;

  }

  @PostMapping("/users{id}")
  public List<Users> addUser(@PathVariable("id") Integer id) throws StripeException {
    Stripe.apiKey = stripeKey;
    Map<String, Object> params = new HashMap<>();
    params.put("amount", 2000);
    params.put("currency", "mxn");
    params.put("source", "tok_amex");
    params.put(
        "description",
        "My First Test Charge (created for API docs)");

    Charge charge = Charge.create(params);
    return userRepository.findUserById(id);
  }

}
