package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.Addresses;

public interface AddressesRepository extends JpaRepository<Addresses, Integer> {}
