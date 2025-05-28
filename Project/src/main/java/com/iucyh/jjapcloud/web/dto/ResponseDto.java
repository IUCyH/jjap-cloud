package com.iucyh.jjapcloud.web.dto;

import com.iucyh.jjapcloud.common.exception.errorcode.CommonErrorCode;
import com.iucyh.jjapcloud.common.exception.errorcode.ErrorCode;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private boolean isSuccess;
    private String code;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(String message, T data) {
        ResponseDto<T> dto = new ResponseDto<>();
        dto.isSuccess = true;
        dto.code = null;
        dto.message = message;
        dto.data = data;
        return dto;
    }

    public static <T> ResponseDto<T> fail(ErrorCode errorCode, @Nullable String message) {
        ResponseDto<T> dto = new ResponseDto<>();
        dto.isSuccess = false;
        dto.code = errorCode.getCode();
        dto.message = message != null ? message : errorCode.getMessage();
        dto.data = null;
        return dto;
    }
}
