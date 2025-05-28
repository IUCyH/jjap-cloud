package com.iucyh.jjapcloud.common.exception;

import com.iucyh.jjapcloud.common.exception.errorcode.CommonErrorCode;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.web.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PARAMETER, "Missing parameter: " + ex.getParameterName()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> handleBindException(BindException e) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PARAMETER, "Validation failed"));
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PARAMETER, "Invalid Request Parameter"));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String propertyName = ex.getPropertyName() == null ? "NONE" : ex.getPropertyName();
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PROPERTY.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PROPERTY, "Parameter Type Mismatch: " + propertyName));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PARAMETER, "JSON Parse Error"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_PARAMETER, "Validation failed"));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.NO_HANDLER_FOUND.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.NO_HANDLER_FOUND, "No Handler Found for Request URL: " + ex.getRequestURL()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(CommonErrorCode.INVALID_METHOD.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.INVALID_METHOD, "Unsupported method"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> handleServiceException(ServiceException e) {
        ServiceErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ResponseDto.fail(errorCode, null));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage()); // TODO: 더 자세히 기록

        return ResponseEntity
                .status(CommonErrorCode.SERVER_ERROR.getHttpStatus())
                .body(ResponseDto.fail(CommonErrorCode.SERVER_ERROR, null));
    }
}
