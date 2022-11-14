package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Transactional
    public void addPayments(List<PaymentRequest> requests) {
        requests.forEach(this::addPayment);
    }

    private void addPayment(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest.getEmployee());
        Payment payment = createPayment(paymentRequest, user);
        paymentRepository.save(payment);
    }

    private Payment createPayment(PaymentRequest paymentRequest, User user) {
        if (paymentRepository.existsByEmployeeAndPeriod(user, paymentRequest.getPeriod())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("User %s has payment for period %s", user.getEmail(), paymentRequest.getPeriod())
            );
        }

        return new Payment(user, paymentRequest.getPeriod(), paymentRequest.getSalary());
    }

    public void updatePayment(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest.getEmployee());
        Payment payment = paymentRepository.findByEmployeeAndPeriod(user, paymentRequest.getPeriod());
        if (payment == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Payment with period " + paymentRequest.getPeriod() + " does not exist"
            );
        }
        payment.setSalary(paymentRequest.getSalary());
        paymentRepository.save(payment);
    }

    public PaymentDetails getPaymentDetails(UserDetails userDetails, String period) {
        User user = getUser(userDetails.getUsername());
        Payment payment = getPayment(user, period);
        return new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary());
    }

    public List<PaymentDetails> getPaymentDetails(UserDetails userDetails) {
        User user = getUser(userDetails.getUsername());
        return paymentRepository.findAllByEmployee(user)
                .stream()
                .map(payment -> new PaymentDetails(
                        payment.getEmployee().getName(),
                        payment.getEmployee().getLastname(),
                        payment.getPeriod(),
                        payment.getSalary())
                )
                .sorted()
                .toList();
    }

    public Payment getPayment(User user, String period) {
        Optional<Payment> payment = Optional.ofNullable(paymentRepository.findByEmployeeAndPeriod(user, period));
        if (payment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment does not exist");
        }

        return payment.get();
    }

    private User getUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not in database");
        }

        return user.get();
    }
}
