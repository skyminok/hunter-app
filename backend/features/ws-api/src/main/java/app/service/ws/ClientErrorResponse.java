package app.service.ws;

import app.Violation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientErrorResponse {

    private String message;
    private List<Violation> violations;
}
