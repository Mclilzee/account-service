package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/api/acct/payments")
    public ResponseEntity<Map<String, String>> addPayments(@RequestBody List<@Valid PaymentRequest> paymentRequests) {
        paymentService.addPayments(paymentRequests);
        return getStatusAddedSuccessfullyResponse("Added successfully!");
    }


    @PutMapping("/api/acct/payments")
    public ResponseEntity<Map<String, String>> updatePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        paymentService.updatePayment(paymentRequest);
        return getStatusAddedSuccessfullyResponse("Updated successfully!");
    }

    private ResponseEntity<Map<String, String>> getStatusAddedSuccessfullyResponse(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("status", message);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping(value = "/api/empl/payment", params = "period")
    public ResponseEntity<PaymentDetails> getPayment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String period) {
        PaymentDetails paymentDetails = paymentService.getPaymentDetails(userDetails, period);

        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<List<PaymentDetails>> getPayment(@AuthenticationPrincipal UserDetails userDetails) {
        List<PaymentDetails> paymentDetails = paymentService.getPaymentDetails(userDetails);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }
}
