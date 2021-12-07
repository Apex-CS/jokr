package apex.ingagers.ecommerce.config;

import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import apex.ingagers.ecommerce.model.Categories;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.CategoriesRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;

@Configuration
public class LoadDatabase {
	@Bean
	CommandLineRunner loadData(SubCategoriesRepository subCategoriesRepository,
			CategoriesRepository categoriesRepository) {
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
					categories.setIs_active(false);
					categories.setCreated_at(sqlTimestamp);
					categories.setUpdated_At(sqlTimestamp);

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
							subCategories.setUpdated_At(sqlTimestamps);
							subCategories.setCategories(categories);
							subCategoriesRepository.save(subCategories);
						}
					}
				}
			}
		};
	}
}
