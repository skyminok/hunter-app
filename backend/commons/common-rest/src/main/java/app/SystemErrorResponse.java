package app;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SystemErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String trace;
    private String message;
    private String path;
}
