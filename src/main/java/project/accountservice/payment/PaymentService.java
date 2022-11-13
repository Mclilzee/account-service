package project.accountservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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

    private User addPaymentToUser(PaymentRequest payment) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(payment.getEmployee()));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is not in database");
        }

        user.get().addPayment(payment.getPeriod(), payment.getSalary());
        return user.get();
    }
}
