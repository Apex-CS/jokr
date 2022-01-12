package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

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
    Addresses addAddresses(@RequestBody Addresses addresses) {

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        Addresses a = addresses;
        a.setCreated_at(sqlTimestamp);
        a.setUpdated_at(null);
        a.setDelete_at(null);

        List<Users> optionalUser = userRepository.findUserById(a.getUsers().getId());
        if (!optionalUser.isEmpty()) {
            Users users = optionalUser.get(0);
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
    public Addresses update(@PathVariable("id") Integer id, @RequestBody Addresses addresses) {

        Optional<Addresses> optionaladdresses = addressesRepository.findById(id);

        if (optionaladdresses.isPresent()) {
            Addresses newaddresses = optionaladdresses.get();
            newaddresses.setStreet1(addresses.getStreet1());
            newaddresses.setStreet2(addresses.getStreet2());
            newaddresses.setColonia(addresses.getColonia());
            newaddresses.setMunicipio(addresses.getMunicipio());
            newaddresses.setState(addresses.getState());
            newaddresses.setCountry(addresses.getCountry());
            newaddresses.setPostal_code(addresses.getPostal_code());
            newaddresses.setPhone(addresses.getPhone());
            newaddresses.setRecipient_name(addresses.getRecipient_name());

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            newaddresses.setUpdated_at(sqlTimestamp);

            return addressesRepository.save(newaddresses);
        }
        return null;
    }
}
