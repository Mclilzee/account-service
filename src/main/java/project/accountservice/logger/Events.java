package project.accountservice.logger;

import org.springframework.web.bind.annotation.CookieValue;

public enum Events {
    CREATE_USER,
     CHANGE_PASSWORD,
    ACCESS_DENIED,
    LOGIN_FAILED,
    GRANT_ROLE,
    REMOVE_ROLE,
    LOCK_USER,
    UNLOCK_USER,
    DELETE_USER,
    BRUTE_FORCE
}
