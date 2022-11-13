package project.accountservice.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountservice.user.User;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByEmployeeAndPeriod(User employee, String period);

    Payment findByEmployeeAndPeriod(User employee, String period);

    List<Payment> findAllByEmployee(User employee);
}
