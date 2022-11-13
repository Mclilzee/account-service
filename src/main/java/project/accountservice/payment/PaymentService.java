package project.accountservice.payment;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.exception.CustomBadRequestError;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void addPayments(List<PaymentRequest> requests) {
        List<User> users = requests.stream()
                .map(this::addPaymentToUser)
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    private User addPaymentToUser(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest);
        user.addPayment(paymentRequest.getPeriod(), paymentRequest.getSalary());
        return user;
    }

    public void updatePayment(PaymentRequest paymentRequest) {
        Payment newPayment = new Payment(paymentRequest.getPeriod(), paymentRequest.getSalary());
        User user = getUser(paymentRequest);
        int index = user.getPayments().indexOf(newPayment);
        user.getPayments().set(index, newPayment);

        userRepository.save(user);
    }

    private User getUser(PaymentRequest paymentRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(paymentRequest.getEmployee()));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not in database");
        }

        return user.get();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<CustomBadRequestError> handleFailedQueryException(
            ConstraintViolationException ex,
            WebRequest request) {
        CustomBadRequestError body = new CustomBadRequestError("User payment period duplicated", request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
