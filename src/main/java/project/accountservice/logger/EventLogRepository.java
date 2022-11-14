package project.accountservice.logger;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventLogRepository extends JpaRepository<EventLog, Long> {

}
