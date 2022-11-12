package project.accountservice.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByEmployeeAndPeriod(String email, String period);

    List<Payment> findAllByEmployee(String email);
}
