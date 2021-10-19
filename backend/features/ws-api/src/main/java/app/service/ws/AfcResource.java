package app.service.ws;

import app.AppPath;
import app.IntegrationApi;
import app.SystemErrorResponse;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AppPath.WS_PATH)
@Api
@IntegrationApi
@Slf4j
@RequiredArgsConstructor
public class AfcResource {

    private final AcceptAfcRequestService service;

    @PostMapping(path = "/case", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Прием на рассмотрение кейсов от ЦПМ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запрос успешно принят к обработке", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Ошибка в запросе", response = ClientErrorResponse.class),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера", response = SystemErrorResponse.class,
                    examples =
                    @Example(value = @ExampleProperty(value = """
                            {
                              "timestamp": "2021-10-19T06:36:35.622+00:00",
                              "status": 500,
                              "error": "Internal Server Error",
                              "trace": ".....",                         
                              "message": "Index out of bounds",
                              "path": "/ws/case"
                            }""", mediaType = "application/json"))
            )
    })
    public SuccessResponse acceptCase(@ApiParam(value = "Краткий список кейсов")
                                      @NotNull @RequestBody String requestBody) {
        service.execute(requestBody);
//        return new SuccessResponse("OK");
        throw new IllegalStateException("Index out of bounds");
    }
}
