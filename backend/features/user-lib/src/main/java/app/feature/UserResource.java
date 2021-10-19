package app.feature;

import app.AppPath;
import app.FrontApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Api
@RequestMapping(path = AppPath.API_PATH + "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@FrontApi
public class UserResource {

    @GetMapping
    @ApiOperation("Список пользователей")
    public List<UserDto> list() {
        return Collections.emptyList();
    }
}
