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
import apex.ingagers.ecommerce.model.OrderProduct;
import apex.ingagers.ecommerce.model.Orders;
import apex.ingagers.ecommerce.model.Products;
import apex.ingagers.ecommerce.model.PurchaseDone;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.model.PurchaseDone.OrderProduct_metadata;
import apex.ingagers.ecommerce.repository.AddressesRepository;
import apex.ingagers.ecommerce.repository.OrderProductRepository;
import apex.ingagers.ecommerce.repository.OrdersRepository;
import apex.ingagers.ecommerce.repository.ProductsRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")

public class OrdersController {

    private final UserRepository userRepository;
    private final AddressesRepository addressesRepository;
    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final OrderProductRepository orderProductRepository;

    OrdersController(UserRepository userRepository, AddressesRepository addressesRepository,
            OrdersRepository ordersRepository, ProductsRepository productsRepository,OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.addressesRepository = addressesRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @PostMapping("/createOrder")
    HttpStatus createOrder(@RequestBody PurchaseDone purchaseDone) {
        // *Create a new order (Need)

        int id_user = purchaseDone.getAddress().getUsers().getId();
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

        int total_cost = purchaseDone.getAmount_total();
        order.setTotal_cost(total_cost);

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        order.setCreated_at(sqlTimestamp);
        order.setUpdated_at(null);
        //
        Orders savedOrder = ordersRepository.save(order);

        // int numOfProducts = purchaseDone.getProducts().size();

        OrderProduct orderProduct = new OrderProduct();

        int id_product;
        int p_quantity;
        float p_price;
        String p_name;
        Products p;
 
    
        for (OrderProduct_metadata product : purchaseDone.getItems()) {
            // id_product= Integer.parseInt(product.id);
            // TODO:el id de producto de stripe no
            // funciona para esto, por lo tanto se buscara el ID dependiendo de la
            // description, esto se debe cambiar

            p = productsRepository.findByDescription(product.description);
            p_quantity = product.quantity;
            p_price = p.getPrice();
            p_name = p.getName();

            orderProduct.setName(p_name);
            orderProduct.setQuantity(p_quantity);
            orderProduct.setPrice(p_price);
            orderProduct.setProducts(p);
            orderProduct.setOrders(savedOrder);

            orderProductRepository.save(orderProduct);
        }

        return HttpStatus.OK;
    }
}