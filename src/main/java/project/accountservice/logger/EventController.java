package project.accountservice.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventLogService eventLogService;

    @GetMapping("/api/security/events")
    public List<EventLog> getEvents() {
        return eventLogService.getEventsLog();
    }

}
