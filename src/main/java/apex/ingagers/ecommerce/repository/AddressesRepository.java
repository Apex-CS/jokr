package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import apex.ingagers.ecommerce.model.Addresses;

public interface AddressesRepository extends JpaRepository<Addresses, Integer> {
    @Query(value = "SELECT * FROM addresses WHERE  is_active = 1", nativeQuery = true)
    List<Addresses> findAllAddresses();

    @Query(value = "SELECT * FROM addresses WHERE  is_active = 1 and id=?1", nativeQuery = true)
    List<Addresses> findAddressesById(Integer id);
    
    //TODO: Modificar para consultar todos los Addresses de un determinado usuario
    @Query(value = "SELECT * FROM addresses WHERE is_active = 1 and id_user=?1", nativeQuery = true)
    List<Addresses> findAddressesByUserId(Integer id_user);

    //TODO: Modificar para consultar todos los Addresses de un determinado usuario
    @Query(value = "SELECT * FROM addresses WHERE is_active = 1 and id_user=?1 and is_default_billing_address=1", nativeQuery = true)
    List<Addresses> findBAddressesByUserId(Integer id_user);

    //TODO: Modificar para consultar todos los Addresses de un determinado usuario
    @Query(value = "SELECT * FROM addresses WHERE is_active = 1 and id_user=?1 and is_default_shipping_address=1", nativeQuery = true)
    List<Addresses> findSAddressesByUserId(Integer id_user);
}
