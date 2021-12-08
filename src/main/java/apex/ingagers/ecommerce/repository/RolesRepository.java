package apex.ingagers.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
   Roles findByRolename(String rolename);

}
