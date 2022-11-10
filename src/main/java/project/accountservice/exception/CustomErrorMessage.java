package project.accountservice.exception;

import java.time.LocalDateTime;

public class CustomErrorMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;

    public CustomErrorMessage(int statusCode,
                              LocalDateTime timestamp,
                              String message) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }
}
