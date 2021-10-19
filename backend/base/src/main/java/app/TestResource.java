package app;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ws")
@Api
@IntegrationApi
public class TestResource {

    @GetMapping(path = "/test")
    public String test() {
        return "OK";
    }
}
