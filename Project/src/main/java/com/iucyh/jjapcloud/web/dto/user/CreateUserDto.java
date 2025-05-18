package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.constant.UserConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank
    @Length(max = UserConstant.NICKNAME_MAX)
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = UserConstant.PASSWORD_MIN, max = UserConstant.PASSWORD_MAX)
    private String password;

    public CreateUserDto() {}
}
