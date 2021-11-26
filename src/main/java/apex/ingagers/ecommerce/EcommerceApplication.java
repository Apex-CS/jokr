package apex.ingagers.ecommerce;

import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import apex.ingagers.ecommerce.model.Categories;
import apex.ingagers.ecommerce.model.SubCategories;
import apex.ingagers.ecommerce.repository.CategoriesRepository;
import apex.ingagers.ecommerce.repository.SubCategoriesRepository;


@SpringBootApplication
public class EcommerceApplication {
	public static void main(String[] args) {
    SpringApplication.run(EcommerceApplication.class, args);
  }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/jokr/add").allowedOrigins("http://localhost:3000");
				registry.addMapping("/jokr/show").allowedOrigins("http://localhost:3000");
				registry.addMapping("/jokr/test").allowedOrigins("http://localhost:3000");
			}
		};
	}

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