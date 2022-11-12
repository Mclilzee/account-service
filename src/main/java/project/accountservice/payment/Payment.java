package project.accountservice.payment;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.time.Period;

@Entity
@JsonPropertyOrder({"employee", "period", "salary"})
public class Payment {

    @Id
    private String employee;

    private Period period;

    @Min(0)
    private long salary;

    public Payment(String employee, Period period, long salary) {
        this.employee = employee;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
