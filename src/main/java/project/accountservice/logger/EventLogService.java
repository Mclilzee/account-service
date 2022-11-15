package project.accountservice.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    @Autowired
    EventLogRepository eventLogRepository;

    public List<EventLog> getEventsLog() {
        return eventLogRepository.findAll();
    }

    public void logCreateUserEvent(String username, String url) {
        EventLog event = new EventLog(Events.CREATE_USER.name(), "Anonymous", username, url);
        eventLogRepository.save(event);
    }

    public void logPasswordChangeEvent(String username, String url) {
        EventLog event = new EventLog(Events.CHANGE_PASSWORD.name(), username, username, url);
        eventLogRepository.save(event);
    }

    public void logLoginFailedEvent(String username, String url) {
        EventLog event = new EventLog(Events.LOGIN_FAILED.name(), username, url, url);
        eventLogRepository.save(event);
    }

    public void logBruteForceEvent(String userName, String url) {
        EventLog bruteForce = new EventLog(Events.BRUTE_FORCE.name(), userName, url, url);
        EventLog lockUser = new EventLog(Events.LOCK_USER.name(), userName, "Lock user " + userName, url);
        eventLogRepository.saveAll(List.of(bruteForce, lockUser));
    }

    public void logAccessDeniedEvent(String name, String requestURI) {
        EventLog event = new EventLog(Events.ACCESS_DENIED.name(), name, requestURI, requestURI);
        eventLogRepository.save(event);
    }

    public void logUserDeletionEvent(String admin, String user, String requestURI) {
        EventLog event = new EventLog(Events.DELETE_USER.name(), admin, user, requestURI);
        eventLogRepository.save(event);
    }

    public void logUserLockingEvent(String username, String user, String requestURI) {
        EventLog event = new EventLog(Events.LOCK_USER.name(), username, user, requestURI);
        eventLogRepository.save(event);
    }

    public void logUserUnlockingEvent(String username, String user, String requestURI) {
        EventLog event = new EventLog(Events.UNLOCK_USER.name(), username, user, requestURI);
        eventLogRepository.save(event);
    }
}
