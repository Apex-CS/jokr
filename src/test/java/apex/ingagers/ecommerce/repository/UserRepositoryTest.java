package apex.ingagers.ecommerce.repository;



import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

import apex.ingagers.ecommerce.User;
import apex.ingagers.ecommerce.UserRepository;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSavedUser(){
        User user = new User();
        user.setName("Robes");
        user.setEmail("Funciona el tests");
        User userSaved = userRepository.save(user);
        Integer compare = 0;
        assertNotEquals(userSaved.getId(), compare);
    }
}
