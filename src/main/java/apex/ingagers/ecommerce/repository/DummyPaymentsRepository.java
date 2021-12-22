package apex.ingagers.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apex.ingagers.ecommerce.model.DummyPayments;

public interface DummyPaymentsRepository extends JpaRepository<DummyPayments, Integer> {

    @Query(value = "SELECT * FROM dummy_payments WHERE id_user=?1", nativeQuery = true)
    List<DummyPayments> findPaymentsByUserId(Integer id);
}
