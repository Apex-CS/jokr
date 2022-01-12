package apex.ingagers.ecommerce.config;

import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import apex.ingagers.ecommerce.model.Categories;
import apex.ingagers.ecommerce.model.OrderStatuses;
import apex.ingagers.ecommerce.model.Roles;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.model.Users;
import apex.ingagers.ecommerce.repository.CategoriesRepository;
import apex.ingagers.ecommerce.repository.OrderStatusesRepository;
import apex.ingagers.ecommerce.repository.RolesRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;
import apex.ingagers.ecommerce.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Configuration
public class LoadDatabase {
	@Bean
	CommandLineRunner loadData(SubCategoriesRepository subCategoriesRepository,
			CategoriesRepository categoriesRepository,RolesRepository rolesRepository, UserRepository userRepository, OrderStatusesRepository orderStatusesRepository) {
		return (args) -> {

			String[][] categoriesAndSubcategories = {
					{ "ROPA", "DEPORTIVA", "CASUAL", "PLAYA" },
					{ "BEBIDAS", "ENERGETICAS","REFRESCOS","AGUA","ALCOHOL" },
					{ "ALIMENTOS", "ARROZ Y SEMILLAS", "ESPECIAS Y SAZONADORES","SALSAS Y ADEREZOS","ENLATADOS Y CONSERVAS","HARINAS"}, 
					{ "SALUD","SANITIZANTES", "DESODORANTES","CUIDADO DEL CABELLO","HIGIENE BUCAL","MEDICINAS","VITAMINAS"},
					{ "ELECTRODOMESTICOS", "ESTUFAS", "REFRIGERADORES","BATIDORAS","LICUADORAS"},
					{ "MASCOTAS", "ALIMENTO PARA PERRO", "JUGUETES", "ALIMENTO PARA GATO", "ACCESORIOS"}
				};

			int categoriesLenght = categoriesAndSubcategories.length;

			for (int i = 0; i < categoriesLenght; i++) {
			
				Categories categories = new Categories();
				String category = categoriesAndSubcategories[i][0];
				
				if (categoriesRepository.findByName(category) == null) {

					categories.setName(category);
					long now = System.currentTimeMillis();
					Timestamp sqlTimestamp = new Timestamp(now);
					categories.setCreated_at(sqlTimestamp);
					categories.setUpdated_At(null);

					categoriesRepository.save(categories);
					int subCategoriesLenght = categoriesAndSubcategories[i].length;

					for (int j = 1; j < subCategoriesLenght; j++) {
					
						SubCategories subCategories = new SubCategories();
						String subCategory = categoriesAndSubcategories[i][j];

						if (subCategoriesRepository.findByName(subCategory) == null) {

							subCategories.setName(subCategory);
							long nows = System.currentTimeMillis();
							Timestamp sqlTimestamps = new Timestamp(nows);
							subCategories.setCreated_at(sqlTimestamps);
							subCategories.setUpdated_At(null);
							subCategories.setCategories(categories);
							subCategoriesRepository.save(subCategories);
						}
					}
				}
			}

			String[] roleNames = { "Shopper", "Admin" };
			long now = System.currentTimeMillis();
			Timestamp sqlTimestamp = new Timestamp(now);
			int roleNamesLenght = roleNames.length;

			for (Integer i=0;i<roleNamesLenght;i++) {
				String rolName = roleNames[i];
				if (rolesRepository.findByRolename(rolName) == null) {
				
					Roles rol = new Roles();
					rol.setrolename(rolName);
					rol.setCreated_at(sqlTimestamp);
					rol.setUpdated_at(null);
					rolesRepository.save(rol);
				}
			}

			Users user = new Users();
			if(userRepository.findByName("John")==null){
				user.setName("John");
				user.setLastName("Doe");
				user.setEmail("john.doe@git.com");
				
				Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
				String hash = argon2.hash(1, 1024, 1, "test123");
				user.setPassword(hash);

				Roles rol;
    			rol = rolesRepository.findByRolename("Admin");

				user.setRole(rol);
				user.setCreated_at(sqlTimestamp);
				user.setDelete_at(null);
				user.setUpdated_at(null);
				userRepository.save(user);
			}

			if(userRepository.findByName("Jane")==null){
				user.setName("Jane");
				user.setLastName("Doe");
				user.setEmail("jane.doe@git.com");

				Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
				String hash = argon2.hash(1, 1024, 1, "test123");
				user.setPassword(hash);

				Roles rol;
    			rol = rolesRepository.findByRolename("Shopper");

				user.setRole(rol);
				user.setCreated_at(sqlTimestamp);
				user.setDelete_at(null);
				user.setUpdated_at(null);
				userRepository.save(user);
			}

			String[] statuses_name = { "Awaiting Payment", "Shipped", "Completed" };
			String[] statuses_description = {"Order petition has been received, still waiting for payment confirmation",
											"Package has been fulfilled and it is on the way",
											"Customer has received the package"};
			int statusNamesLenght = statuses_name.length;

			for (Integer i=0;i<statusNamesLenght;i++) {
				String status_name = statuses_name[i];
				String status_description = statuses_description[i];
				if (orderStatusesRepository.findByStatusname(status_name) == null) {
					
					OrderStatuses order_status = new OrderStatuses();
					order_status.setStatus_name(status_name);
					order_status.setDescription(status_description);
					order_status.setCreated_at(sqlTimestamp);
					order_status.setUpdated_at(null);
					orderStatusesRepository.save(order_status);
				}
			}
			
		};
	}

}
