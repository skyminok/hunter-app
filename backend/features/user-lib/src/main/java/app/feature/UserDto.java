package app.feature;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    @ApiModelProperty(value = "ID")
    private UUID id;

    @ApiModelProperty(value = "Логин")
    private String username;

    @ApiModelProperty(value = "ФИО пользователя")
    private String fullName;
}
