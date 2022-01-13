package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Addresses;
import apex.ingagers.ecommerce.model.Orders;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.AddressesRepository;
import apex.ingagers.ecommerce.repository.OrdersRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
@RestController
@RequestMapping("/api/v1")

public class OrdersController {

    private final UserRepository userRepository;
    private final AddressesRepository addressesRepository;
    private final OrdersRepository ordersRepository;

    OrdersController(UserRepository userRepository, AddressesRepository addressesRepository,
            OrdersRepository ordersRepository) {
        this.userRepository = userRepository;
        this.addressesRepository = addressesRepository;
        this.ordersRepository = ordersRepository;
    }

    @PostMapping("/createOrder") 
    HttpStatus createOrder(@RequestBody Map<String, Object> values) {
        //*Create a new order (Need)
        int id_user = Integer.parseInt(String.valueOf(values.get("id_user")));
        Orders order = new Orders();

        List<Users> optionalUser = userRepository.findUserById(id_user);
        if (!optionalUser.isEmpty()) {
            Users user = optionalUser.get(0);
            order.setUsers(user);
        }

        List<Addresses> optionalBAddress = addressesRepository.findBAddressesByUserId(id_user);
        if (!optionalBAddress.isEmpty()) {
            Addresses BillingAddress = optionalBAddress.get(0);
            order.setBilling_address(BillingAddress);
        }

        List<Addresses> optionalSAddress = addressesRepository.findSAddressesByUserId(id_user);
        if (!optionalSAddress.isEmpty()) {
            Addresses Shippingaddress = optionalSAddress.get(0);
            order.setShipping_address(Shippingaddress);
        }
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        order.setCreated_at(sqlTimestamp);
        order.setUpdated_at(null);
        ordersRepository.save(order);
        // Orders savedOrder = 
        // int orderId = savedOrder.getId();

        return HttpStatus.OK;
    }
}