package project.accountservice.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    @PostMapping("/api/acct/payments")
    public ResponseEntity<Object> addPayments(@Valid @RequestBody List<Payment> payments) {

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
