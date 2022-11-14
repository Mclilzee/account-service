package project.accountservice.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Service
public class EventLogService {

    @Autowired
    EventLogRepository eventLogRepository;

    public List<EventLog> getEventsLog() {
        return eventLogRepository.findAll();
    }

    public void logCreateUserEvent(String username, ServletWebRequest request) {
        EventLog event = new EventLog(Events.CREATE_USER.name(), "Anonymous", username, getUrl(request));
        eventLogRepository.save(event);
    }

    public void logPasswordChangeEvent(String username, ServletWebRequest request) {
        EventLog event = new EventLog(Events.CHANGE_PASSWORD.name(), username, username, getUrl(request));
        eventLogRepository.save(event);
    }

    public void logLoginFailedEvent(String username) {
        String url = getUrl();
        EventLog event = new EventLog(Events.LOGIN_FAILED.name(), username, url, url);
        eventLogRepository.save(event);
    }

    private String getUrl() {
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (request == null) {
            return "/";
        }
        return request.getRequest().getRequestURI();
    }

    private String getUrl(ServletWebRequest request) {
        return request.getRequest().getRequestURI();
    }
}
