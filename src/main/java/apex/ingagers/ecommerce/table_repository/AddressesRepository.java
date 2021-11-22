package apex.ingagers.ecommerce.table_repository;

import org.springframework.data.repository.CrudRepository;

import apex.ingagers.ecommerce.tables.Addresses;

public interface AddressesRepository extends CrudRepository<Addresses, Integer> {}
