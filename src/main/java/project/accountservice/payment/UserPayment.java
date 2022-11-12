package project.accountservice.payment;

import java.time.Month;

public class UserPayment {

    private final String name;
    private final String lastname;
    private final String period;
    private final String salary;

    public UserPayment(String name, String lastname, String period, long salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = formatPeriod(period);
        this.salary = formatSalary(salary);
    }

    private String formatSalary(long salary) {
        return "%d dollars(s) %d cent(s)".formatted(salary / 100, salary % 100);
    }

    private String formatPeriod(String period) {
        String[] periods = period.split("-");
        Month month = Month.of(Integer.parseInt(periods[0]));
        String capitalizedMonth = month.toString().substring(0, 1).toUpperCase() + month.toString().substring(1).toLowerCase();
        return "%s-%s".formatted(capitalizedMonth, periods[1]);
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPeriod() {
        return period;
    }

    public String getSalary() {
        return salary;
    }
}