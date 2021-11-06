package apex.ingagers.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EcommerceApplication {

	@RequestMapping("/welcome")
	public String welcome() {
		return "Hello Docker World!";
	}
	
	@RequestMapping("/goodbye")
	public String goodbye() {
		return "Bye Docker World!";
	}
	
	@RequestMapping("/")
	public String index() {
		return "Index";
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}