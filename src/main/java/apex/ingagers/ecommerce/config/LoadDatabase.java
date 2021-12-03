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
	CommandLineRunner loadData(SubCategoriesRepository subCategoriesRepository, CategoriesRepository categoriesRepository){
		return (args) -> {
				Categories categories = new Categories();
			 	
				if(categoriesRepository.findByName("ROPA") == null){
				
					categories.setName("ROPA");				
					long now = System.currentTimeMillis();
					Timestamp sqlTimestamp = new Timestamp(now);

					categories.setCreated_at(sqlTimestamp);
					categories.setUpdated_At(sqlTimestamp);

					categoriesRepository.save(categories);
			  }
			  
			  SubCategories subCategories = new SubCategories();
				if(subCategoriesRepository.findByName("DEPORTIVA") == null){
				
					subCategories.setName("DEPORTIVA");
					long nows = System.currentTimeMillis();
					Timestamp sqlTimestamps = new Timestamp(nows);
					subCategories.setCreated_at(sqlTimestamps);
					subCategories.setUpdated_At(sqlTimestamps);
					subCategories.setCategories(categories);
					subCategoriesRepository.save(subCategories);
			 }
		};
	}
}
