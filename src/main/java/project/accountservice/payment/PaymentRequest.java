package project.accountservice.payment;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class PaymentRequest {

    private String employee;

    @Pattern(regexp = "(0[1-9]|1[0-2])-20\\d{2}", message = "Incorrect period format")
    private String period;

    @Min(value = 0, message = "Salary cannot be negative")
    private long salary;

    @JsonCreator
    public PaymentRequest(String employee, String period, long salary) {
        this.employee = employee.toLowerCase();
        this.period = period;
        this.salary = salary;
    }

    public PaymentRequest() {
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
}
