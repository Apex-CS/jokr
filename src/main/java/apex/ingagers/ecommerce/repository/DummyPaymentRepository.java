package apex.ingagers.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apex.ingagers.ecommerce.model.DummyPayment;
import apex.ingagers.ecommerce.model.TestModel;

public interface DummyPaymentRepository extends JpaRepository <DummyPayment, Integer> {
    
}
