package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apex.ingagers.ecommerce.model.TestModel;
import apex.ingagers.ecommerce.repository.TestRepository;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    private final TestRepository testRepository;

    TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PreAuthorize("hasAuthority ('Admin')")
    @GetMapping("/test")
    TestModel testDate() {

        TestModel t = new TestModel();
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);

        t.setTime_now(sqlTimestamp);

        return testRepository.save(t);

    }
}
