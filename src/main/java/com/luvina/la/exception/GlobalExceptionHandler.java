package com.luvina.la.exception;

import com.luvina.la.payload.ErrorResponse;
import com.luvina.la.payload.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler class để xử lý các exception toàn cục.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý ngoại lệ BusinessException
     * @param ex: ngoại lệ BusinessException
     * @return: response chứa mã lỗi và thông báo lỗi
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        // Tạo thông báo lỗi dựa trên mã lỗi và các tham số
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode(), ex.getParams());

        // Tạo ApiErrorResponse với mã lỗi 500 và thông báo lỗi ER015
        ErrorResponse ErrorResponse = new ErrorResponse("500", errorMessage);

        // Trả về response với mã HTTP 500 và nội dung lỗi

        return new ResponseEntity<>(ErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
