package apex.ingagers.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class EcommerceApplication {
	public static void main(String[] args) {
    SpringApplication.run(EcommerceApplication.class, args);
  }

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
	// 		}
	// 	};
	// }

}