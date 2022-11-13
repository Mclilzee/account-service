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

import java.util.ArrayList;
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
        List<Payment> newPayments = new ArrayList<>(user.getPayments());
        newPayments.add(new Payment(paymentRequest.getPeriod(), paymentRequest.getSalary()));
        user.setPayments(newPayments);
        return user;
    }

    public void updatePayment(PaymentRequest paymentRequest) {
        User user = getUser(paymentRequest);
        Payment newPayment = new Payment(paymentRequest.getPeriod(), paymentRequest.getSalary());
        updateUserPayment(user, newPayment);
        userRepository.save(user);
    }

    private void updateUserPayment(User user, Payment newPayment) {
        int index = user.getPayments().indexOf(newPayment);
        if (index == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment period does not exist");
        }
        user.getPayments().set(index, newPayment);
    }

    public PaymentDetails getPaymentDetails(User user, String period) {
        Payment payment = getPayment(user, period);
        return new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary());
    }

    public List<PaymentDetails> getPaymentDetails(User user) {
        return user.getPayments()
                .stream()
                .map(payment -> new PaymentDetails(user.getName(), user.getLastname(), payment.getPeriod(), payment.getSalary()))
                .toList();
    }

    public Payment getPayment(User user, String period) {
        List<Payment> payments = user.getPayments().stream()
                .filter(payment -> payment.getPeriod().equals(period))
                .limit(1)
                .collect(Collectors.toList());
        if (payments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment does not exist");
        }

        return payments.get(0);
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
