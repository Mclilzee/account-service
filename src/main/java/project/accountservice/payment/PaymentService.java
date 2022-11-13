package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.exception.CustomBadRequestError;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void addPayments(List<PaymentRequest> requests) {
        requests.forEach(this::addPaymentToUser);
    }

    private void addPaymentToUser(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest);
        user.getPayments().put(paymentRequest.getPeriod(), new Payment(paymentRequest.getPeriod(), paymentRequest.getSalary()));
        userRepository.save(user);
    }

    public void updatePayment(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest);
        updateUserPayment(user, paymentRequest.getPeriod(), paymentRequest.getSalary());
        userRepository.save(user);
    }

    private void updateUserPayment(User user, String period, long salary) {
        if (!user.getPayments().containsKey(period)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment period does not exist");
        }
        user.getPayments().get(period).setSalary(salary);
    }

    public PaymentDetails getPaymentDetails(User user, String period) {
        Payment payment = getPayment(user, period);
        return new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary());
    }

    public List<PaymentDetails> getPaymentDetails(User user) {
        return user.getPayments().values()
                .stream()
                .map(payment -> new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary()))
                .toList();
    }

    public Payment getPayment(User user, String period) {
        Optional<Payment> payment = Optional.ofNullable(user.getPayments().get(period));
        if (payment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment does not exist");
        }

        return payment.get();
    }

    private User getUser(PaymentRequest paymentRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(paymentRequest.getEmployee()));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not in database");
        }

        return user.get();
    }

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<CustomBadRequestError> handleFailedQueryException(
            TransactionSystemException ex,
            WebRequest request) {
        CustomBadRequestError body = new CustomBadRequestError("User payment period duplicated", request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
