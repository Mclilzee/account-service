package project.accountservice.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import project.accountservice.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "period_per_employee", columnNames = {"employee_id", "period"}))
@JsonPropertyOrder({"employee", "period", "salary"})
public class Payment implements Comparable<Payment> {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User user;

    @Pattern(regexp = "(0[1-9]|1[0-2])-20\\d{2}", message = "Incorrect period format")
    private String period;

    @Min(value = 0, message = "Salary cannot be negative")
    private Long salary;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonCreator
    public Payment(String period, Long salary) {
        this.period = period;
        this.salary = salary;
    }

    public Payment() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(period, payment.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period);
    }

    @Override
    public int compareTo(Payment o) {
        return o.period.compareTo(this.period);
    }
}
