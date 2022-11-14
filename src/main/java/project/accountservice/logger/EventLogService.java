package project.accountservice.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Service
public class EventLogService {

    @Autowired
    EventLogRepository eventLogRepository;

    public List<EventLog> getEventsLog() {
        return eventLogRepository.findAll();
    }

    public void logCreateUserEvent(String username, ServletWebRequest request) {
        EventLog event = new EventLog(Events.CREATE_USER.name(), "Anonymous", username, request.getRequest().getRequestURI());
        eventLogRepository.save(event);
    }

    public void logPasswordChangeEvent(String username, ServletWebRequest request) {
        EventLog event = new EventLog(Events.CHANGE_PASSWORD.name(), username, username, request.getRequest().getRequestURI());
        eventLogRepository.save(event);
    }
}
