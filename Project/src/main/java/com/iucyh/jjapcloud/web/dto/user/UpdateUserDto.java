package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.constant.UserConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateUserDto {

    @NotBlank
    @Length(max = UserConstant.NICKNAME_MAX)
    private String nickname;

    @NotBlank
    @Length(min = UserConstant.PASSWORD_MIN, max = UserConstant.PASSWORD_MAX)
    private String password;

    public UpdateUserDto() {}
}
