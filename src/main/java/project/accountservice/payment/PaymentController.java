package project.accountservice.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class PaymentController {

    @PostMapping("/api/acct/payments")
    public ResponseEntity<Map<String, String>> addPayments(@RequestBody List<@Valid Payment> payments) {

        Map<String, String> response = new HashMap<>();
        response.put("status", "Added successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
