package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.exception.CustomBadRequestError;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserRepository userRepository;

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
        User user = userRepository.findByEmail(userDetails.getUsername());
        PaymentDetails paymentDetails = paymentService.getPaymentDetails(user, period);

        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<List<PaymentDetails>> getPayment(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername());

        List<Payment> payments = paymentRepository.findAllByEmployee(user.getEmail());
        List<PaymentDetails> paymentDetails = getUserPayments(user, payments);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }

    private List<PaymentDetails> getUserPayments(User user, List<Payment> payments) {
        return payments
                .stream()
                .map(payment -> new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary()))
                .toList();
    }
}
