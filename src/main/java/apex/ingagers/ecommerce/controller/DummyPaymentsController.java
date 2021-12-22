package apex.ingagers.ecommerce.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apex.ingagers.ecommerce.model.DummyPayments;
import apex.ingagers.ecommerce.repository.DummyPaymentsRepository;

@RestController
@RequestMapping("/api/v1")
public class DummyPaymentsController {

    private final DummyPaymentsRepository dummyPaymentRepository;

    DummyPaymentsController(DummyPaymentsRepository dummyPaymentRepository) {
        this.dummyPaymentRepository = dummyPaymentRepository;
    }

    @PostMapping("/dummyPay") // Map ONLY POST Requests
    HttpStatus intentPayment(@RequestBody DummyPayments dummypayment) {

        String card = dummypayment.getCardNumber().replaceAll("\\s", "");
        String expMonth = dummypayment.getExpMonth().replaceAll("\\s", "");
        String expYear = dummypayment.getExpYear().replaceAll("\\s", "");
        String securityCode = dummypayment.getSecurityCode().replaceAll("\\s", "");

        Calendar date = Calendar.getInstance();
        String cardMonth = String.valueOf(date.get(Calendar.MONTH) + 1);
        String cardYear = String.valueOf(date.get(Calendar.YEAR));

        if (!card.matches("[0-9]+")) {
            if (card != "4111111111111111" || card.length() != 16) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "1    Please verify your credit card data");
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "1    Please verify your credit card data");
        }

        if (!expMonth.matches("[0-9]+")) {
            if (expMonth != cardMonth || expMonth.length() != 2) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Please verify your credit card data");
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "1    Please verify your credit card data");
        }

        if (!expYear.matches("[0-9]+")) {
            if (expYear.length() != 2) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Please verify your credit card data");
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "1    Please verify your credit card data");
        }

        if (!securityCode.matches("[0-9]+")) {
            if (expYear != cardYear || securityCode.length() != 3) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Please verify your credit card data");
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "1    Please verify your credit card data");
        }
        DummyPayments iP = new DummyPayments();
        iP = dummypayment;
        iP.setStatus("Pending");
        iP.setCurrency("USD");

        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        iP.setCreated_at(sqlTimestamp);

        if (dummyPaymentRepository.save(iP) != null) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/dummyPay/{id_dummyPayment}") // Map ONLY POST Requests
    HttpStatus approvePayment(@PathVariable("id_dummyPayment") Integer id_dummyPayment) {

        Optional<DummyPayments> dummyPayment = dummyPaymentRepository.findById(id_dummyPayment);

        if (dummyPayment.isPresent()) {
            DummyPayments payment = dummyPayment.get();
            payment.setStatus("Approved");
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            payment.setUpdated_at(sqlTimestamp);
            if (dummyPaymentRepository.save(payment) != null) {
                return HttpStatus.OK;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/dummyPay/reject/{id_dummyPayment}") // Map ONLY POST Requests
    HttpStatus rejectPayment(@PathVariable("id_dummyPayment") Integer id_dummyPayment) {

        Optional<DummyPayments> dummyPayment = dummyPaymentRepository.findById(id_dummyPayment);

        if (dummyPayment.isPresent()) {
            DummyPayments payment = dummyPayment.get();
            payment.setStatus("Rejected");
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            payment.setUpdated_at(sqlTimestamp);
            if (dummyPaymentRepository.save(payment) != null) {
                return HttpStatus.OK;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/dummyPay/{id_user}")
    public List<DummyPayments> getDummyPaymentsByUserId(@PathVariable("id_user") Integer id_user) {
        return dummyPaymentRepository.findPaymentsByUserId(id_user);
    }

    @GetMapping("/dummyPay")
    public List<DummyPayments> getAllDummyPayments() {
        return dummyPaymentRepository.findAll();
    }

}
