package com.iucyh.jjapcloud.web.common.exception;

import com.iucyh.jjapcloud.web.common.exception.errorcode.ErrorCode;
import com.iucyh.jjapcloud.web.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.web.dto.RequestFailedDto;
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
                .status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PARAMETER.getHttpStatus().value(), ErrorCode.INVALID_PARAMETER.getCode(), "Missing Request Parameter: " + ex.getParameterName()));
    }

    @ExceptionHandler
    public ResponseEntity<RequestFailedDto> handleBindException(BindException e) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PARAMETER.getHttpStatus().value(), ErrorCode.INVALID_PARAMETER.getCode(), "Validation failed"));
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PARAMETER.getHttpStatus().value(), ErrorCode.INVALID_PARAMETER.getCode(), "Invalid Request Parameter"));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String propertyName = ex.getPropertyName() == null ? "NONE" : ex.getPropertyName();
        return ResponseEntity
                .status(ErrorCode.INVALID_PROPERTY.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PROPERTY.getHttpStatus().value(), ErrorCode.INVALID_PROPERTY.getCode(), "Parameter Type Mismatch: " + propertyName));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PARAMETER.getHttpStatus().value(), ErrorCode.INVALID_PARAMETER.getCode(), "JSON Parse Error"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_PARAMETER.getHttpStatus().value(), ErrorCode.INVALID_PARAMETER.getCode(), "Validation failed"));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(ErrorCode.NO_HANDLER_FOUND.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.NO_HANDLER_FOUND.getHttpStatus().value(), ErrorCode.NO_HANDLER_FOUND.getCode(), "No Handler Found for Request URL: " + ex.getRequestURL()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(ErrorCode.INVALID_METHOD.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.INVALID_METHOD.getHttpStatus().value(), ErrorCode.INVALID_METHOD.getCode(), "Unsupported method"));
    }

    @ExceptionHandler
    public ResponseEntity<RequestFailedDto> handleServiceException(ServiceException e) {
        ServiceErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(RequestFailedDto.of(errorCode.getHttpStatus().value(), errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<RequestFailedDto> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage()); // TODO: 더 자세히 기록

        return ResponseEntity
                .status(ErrorCode.SERVER_ERROR.getHttpStatus())
                .body(RequestFailedDto.of(ErrorCode.SERVER_ERROR.getHttpStatus().value(), ErrorCode.SERVER_ERROR.getCode(), "Internal Server Error"));
    }
}
