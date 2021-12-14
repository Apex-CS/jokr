package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.Addresses;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.AddressesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class AddressesController {
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;

    AddressesController(AddressesRepository addressesRepository, UserRepository userRepository) {
        this.addressesRepository = addressesRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addresses") // Map ONLY POST Requests
    Addresses addAddresses(@RequestBody Map<String, Object> values) {
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

        Addresses a = new Addresses();
        a.setStreet1(street1);
        a.setStreet2(street2);
        a.setColonia(colonia);
        a.setMunicipio(municipio);
        a.setState(state);
        a.setCountry(country);
        a.setPostal_code(postal_code);
        a.setPhone(phone);
        a.setClient_name(client_name);
        a.setCreated_at(sqlTimestamp);
        a.setUpdated_at(null);
        a.setDelete_at(null);

        Optional<Users> optionalUser = userRepository.findUserById(id_user);
        if (optionalUser.isPresent()) {
            Users users = optionalUser.get();
            a.setUsers(users);
        }

        return addressesRepository.save(a);
    }

    @GetMapping("/addresses/{id_user}")
    public List<Addresses> getAllUserAddresses(@PathVariable("id_user") Integer id_user) {
        return addressesRepository.findAddressesByUserId(id_user);
    }

    @DeleteMapping("/addresses/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        Optional<Addresses> optionaladdresses = addressesRepository.findById(id);
    
        if (optionaladdresses.isPresent()) {
          Addresses addresses = optionaladdresses.get();
          if (addresses.getIs_active() == true) {
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            addresses.setIs_active(false);
            addresses.setDelete_at(sqlTimestamp);
            addressesRepository.save(addresses);
            return true;
          } else {
            return false;
          }
        } else {
          return false;
        }
      }

    @PutMapping("/addresses/{id}")
    public Addresses update(@PathVariable("id") Integer id, @RequestBody Map<String, Object> values) {

        Optional<Addresses> optionaladdresses = addressesRepository.findById(id);

        if (optionaladdresses.isPresent()) {
            Addresses addresses = optionaladdresses.get();
            addresses.setStreet1(String.valueOf(values.get("street1")));
            addresses.setStreet2(String.valueOf(values.get("street2")));
            addresses.setColonia(String.valueOf(values.get("colonia")));
            addresses.setMunicipio(String.valueOf(values.get("municipio")));
            addresses.setState(String.valueOf(values.get("state")));
            addresses.setCountry(String.valueOf(values.get("country")));
            addresses.setPostal_code(String.valueOf(values.get("postal_code")));
            addresses.setPhone(String.valueOf(values.get("phone")));
            addresses.setClient_name(String.valueOf(values.get("client_name")));

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            addresses.setUpdated_at(sqlTimestamp);

            return addresses;
        }
        return null;
    }
}
