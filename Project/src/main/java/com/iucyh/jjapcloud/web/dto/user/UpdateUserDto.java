package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.jjapcloud.constant.UserConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateUserDto {

    @Length(max = UserConstant.NICKNAME_MAX)
    private String nickname;

    @Length(min = UserConstant.PASSWORD_MIN, max = UserConstant.PASSWORD_MAX)
    private String password;

    public UpdateUserDto() {}
}
