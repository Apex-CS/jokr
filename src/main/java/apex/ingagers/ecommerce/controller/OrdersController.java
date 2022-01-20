package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Addresses;
import apex.ingagers.ecommerce.model.OrderProduct;
import apex.ingagers.ecommerce.model.OrderStatuses;
import apex.ingagers.ecommerce.model.Orders;
import apex.ingagers.ecommerce.model.Products;
import apex.ingagers.ecommerce.model.Users;

import apex.ingagers.ecommerce.repository.AddressesRepository;
import apex.ingagers.ecommerce.repository.OrderProductRepository;

import apex.ingagers.ecommerce.repository.OrderStatusesRepository;
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
    private final OrderStatusesRepository orderStatusesRepository;

    OrdersController(UserRepository userRepository, AddressesRepository addressesRepository,
            OrdersRepository ordersRepository, ProductsRepository productsRepository,
            OrderProductRepository orderProductRepository,
            OrderStatusesRepository orderStatusesRepository) {
        this.userRepository = userRepository;
        this.addressesRepository = addressesRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderStatusesRepository = orderStatusesRepository;
    }
    
    @PreAuthorize("hasAuthority ('Shopper')")
    @PostMapping("/orders")
    HttpStatus createOrder(@RequestBody Map<String, Object> values) {

        Map<String, Object> ad = (Map<String, Object>) values.get("address");
        Integer id_user = Integer.parseInt(String.valueOf(ad.get("id_User")));

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

        Float at = Float.parseFloat(String.valueOf(values.get("amount_total")));
        order.setTotal_cost(at);

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        order.setCreated_at(sqlTimestamp);
        order.setUpdated_at(null);

        // * Sets order status to "Awaiting Payment" when the order is created
        List<OrderStatuses> optionalOrderStatus = orderStatusesRepository.findStatusById(1);
        OrderStatuses orderStatus = optionalOrderStatus.get(0);
        order.setOrderStatus(orderStatus);
        
        Orders savedOrder = ordersRepository.save(order);

        Map<String, Object> items = (Map<String, Object>) values.get("items");
        Integer productsQuantity = ((java.util.List) ((java.util.Map.Entry) items.entrySet().toArray()[1]).getValue())
                .size();

        // int id_product; //TODO: Descomentar si se envia desde el front el ID del producto
        int p_quantity;
        float p_price;
        String p_name;

        for (int i = 0; i < productsQuantity; i++) {
            // id_product= Integer.parseInt(product.id); //TODO: Descomentar si se envia desde el front el ID del producto
            // TODO: el id de producto de stripe no
            // funciona para esto, por lo tanto se buscara el ID dependiendo de la
            // description, esto se debe cambiar
            // *5 description, 7 quantity, 0 ID [stripe id no sirve]
            OrderProduct orderProduct = new OrderProduct();
            Products p = new Products();

            String description = String
                    .valueOf(((java.util.Map.Entry) ((java.util.Map) ((java.util.List) ((java.util.Map.Entry) items
                            .entrySet().toArray()[1]).getValue()).get(i)).entrySet().toArray()[5]).getValue());
            Integer quantity = (Integer) ((java.util.Map.Entry) ((java.util.Map) ((java.util.List) ((java.util.Map.Entry) items
                    .entrySet().toArray()[1]).getValue()).get(i)).entrySet().toArray()[7]).getValue();

            p = productsRepository.findByDescription(description);
            p_quantity = quantity;
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

    @GetMapping("/orders/{id_user}")
    public List<Object> getOrdersByUserId(@PathVariable("id_user") Integer id_user) {
        List<Orders> orders = ordersRepository.findOrdersByUserId(id_user);

        List<Object> ordersFinal = new ArrayList<>();

        for (Orders order : orders) {
            ordersFinal.add(order);
            Integer order_id = order.getId();

            List<OrderProduct> productOrders = orderProductRepository.findOrderProductsByOrderId(order_id);

            for (OrderProduct orderProduct : productOrders) {

                ordersFinal.add("Quantity: " + orderProduct.getQuantity());
                ordersFinal.add(orderProduct.getProducts());
            }

        }

        return ordersFinal;
    }
    @PreAuthorize("hasAuthority ('Admin')")
    @GetMapping("/orders/")
    public List<Object> getAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        
        List<Object> ordersFinal = new ArrayList<>();

        for (Orders order : orders) {
            ordersFinal.add(order);
            Integer order_id = order.getId();
            List<OrderProduct> productOrders = orderProductRepository.findOrderProductsByOrderId(order_id);

            for (OrderProduct orderProduct : productOrders) {

                ordersFinal.add("Quantity: " + orderProduct.getQuantity());
                ordersFinal.add(orderProduct.getProducts());
            }

        }

        return ordersFinal;
    }

    @PutMapping("/orders/{id}")
    public Orders update(@PathVariable("id") Integer id, @RequestBody Orders orders) {

        Optional<Orders> optionalOrders = ordersRepository.findById(id);

        if (optionalOrders.isPresent()) {
            Orders newOrder = optionalOrders.get();
            newOrder.setOrderStatus(orders.getOrderStatus());

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            newOrder.setUpdated_at(sqlTimestamp);

            return ordersRepository.save(newOrder);
        }
        return null;
    }
}