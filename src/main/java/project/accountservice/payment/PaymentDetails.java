package project.accountservice.payment;

import project.accountservice.util.StringUtil;

import java.time.Month;

public class PaymentDetails implements Comparable<PaymentDetails> {

    private final String name;
    private final String lastname;
    private final String period;
    private final long salary;

    public PaymentDetails(String name, String lastname, String period, long salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = period;
        this.salary = salary;
    }

    private String formatSalary(long salary) {
        return "%d dollar(s) %d cent(s)".formatted(salary / 100, salary % 100);
    }

    private String formatPeriod(String period) {
        String[] periods = period.split("-");
        Month month = Month.of(Integer.parseInt(periods[0]));
        return "%s-%s".formatted(StringUtil.capitalize(month.toString()), periods[1]);
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPeriod() {
        return formatPeriod(period);
    }

    public String getSalary() {
        return formatSalary(salary);
    }

    @Override
    public int compareTo(PaymentDetails other) {
        return other.period.compareTo(this.period);
    }
}
