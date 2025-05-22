package com.iucyh.jjapcloud.web.dto.auth;

import com.iucyh.jjapcloud.constant.UserConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = UserConstant.PASSWORD_MIN, max = UserConstant.PASSWORD_MAX)
    private String password;

    public LoginDto() {}
}
