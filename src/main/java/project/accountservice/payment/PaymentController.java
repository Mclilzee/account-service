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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class PaymentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/api/acct/payments")
    public ResponseEntity<Map<String, String>> addPayments(@RequestBody List<@Valid Payment> payments) {
        if (!usersInDatabase(payments)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user in list");
        }

        addTransactions(payments);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Added successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    private void addTransactions(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    private boolean usersInDatabase(List<Payment> payments) {
        return payments.stream().allMatch(payment -> userRepository.existsByEmail(payment.getEmployee()));
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    protected ResponseEntity<CustomBadRequestError> handleFailedQueryException(
            org.hibernate.exception.ConstraintViolationException ex,
            WebRequest request) {
        CustomBadRequestError body = new CustomBadRequestError("User payment period duplicated", request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<UserPayment> getPayment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String period) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        Payment payment = paymentRepository.findByEmployeeAndPeriod(user.getEmail(), period);
        UserPayment userPayment = new UserPayment(user.getName(), user.getLastname(), period, payment.getSalary());

        return new ResponseEntity<>(userPayment, HttpStatus.OK);
    }

//    @GetMapping("/api/empl/payment")
//    public ResponseEntity<Map<String, String>> getPayment(@AuthenticationPrincipal UserDetails userDetails) {
//
//    }
}
