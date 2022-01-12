package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
   Roles findByRolename(String rolename);
}
