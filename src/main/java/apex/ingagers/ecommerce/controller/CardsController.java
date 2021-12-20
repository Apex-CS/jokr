package apex.ingagers.ecommerce.controller;

import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.CardsRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class CardsController {

    @Value("${stripe.apikey}")
    String stripeKey;

    private final CardsRepository cardsRepository;
    
    CardsController(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
      }

    
    // @PostMapping("/addCard") 
    // public Optional<Users> addUser(@PathVariable("id")Integer id )throws StripeException {
    //     Stripe.apiKey=stripeKey;
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("amount", 2000);
    //     params.put("currency", "mxn");
    //     params.put("source", "tok_amex");
    //     params.put(
    //       "description",
    //       "My First Test Charge (created for API docs)"
    //     );
        
    //     Charge charge = Charge.create(params);
    //     // return userRepository.findUserById(id);
    // }

}
