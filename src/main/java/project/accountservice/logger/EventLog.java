package project.accountservice.logger;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "events_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private String action;
    private String subject;
    private String object;
    private String path;

    public EventLog(String action, String subject, String object, String path) {
        this.date = LocalDate.now();
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public EventLog(){}

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public String getSubject() {
        return subject;
    }

    public String getObject() {
        return object;
    }

    public String getPath() {
        return path;
    }
}
