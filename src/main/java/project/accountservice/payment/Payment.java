package project.accountservice.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "period_per_employee", columnNames = {"employee", "period"}))
@JsonPropertyOrder({"employee", "period", "salary"})
public class Payment implements Comparable<Payment> {

    @Id
    @GeneratedValue
    private long id;

    private String employee;

    @Pattern(regexp = "(0[1-9]|1[0-2])-20\\d{2}", message = "Incorrect period format")
    private String period;

    @Min(value = 0, message = "Salary cannot be negative")
    private Long salary;

    @JsonCreator
    public Payment(String employee, String period, Long salary) {
        this.employee = employee.toLowerCase();
        this.period = period;
        this.salary = salary;
    }

    public Payment() {
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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
    public int compareTo(Payment o) {
        return o.period.compareTo(this.period);
    }
}
