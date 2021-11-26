package apex.ingagers.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.model.Addresses;

public interface AddressesRepository extends CrudRepository<Addresses, Integer> {}
