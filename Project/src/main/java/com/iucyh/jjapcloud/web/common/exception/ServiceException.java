package com.iucyh.jjapcloud.web.common.exception;

import com.iucyh.jjapcloud.web.common.exception.errorcode.ServiceErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private ServiceErrorCode errorCode;
}
