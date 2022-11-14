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
}
