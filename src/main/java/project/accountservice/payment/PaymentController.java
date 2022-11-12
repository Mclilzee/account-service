package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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

        validateUsers(payments);

        addTransactions(payments);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Added successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    private void addTransactions(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    private void validateUsers(List<@Valid Payment> payments) {
        if (userIsDuplicated(payments)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is duplicated");
        } else if (usersHaveSamePeriod(payments)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users have same period");
        } else if (!usersInDatabase(payments)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user in list");
        }
    }

    private boolean userIsDuplicated(List<Payment> payments) {
        return !payments.stream().map(Payment::getEmployee).allMatch(new HashSet<>()::add);
    }

    private boolean usersInDatabase(List<Payment> payments) {
        return payments.stream().allMatch(payment -> userRepository.existsByEmail(payment.getEmployee()));
    }

    private boolean usersHaveSamePeriod(List<Payment> payments) {
        return !payments.stream().map(Payment::getPeriod).allMatch(new HashSet<>()::add);
    }
}
