package com.luvina.la.exception;

/**
 * BusinessException là ngoại lệ dùng để xử lý các lỗi nghiệp vụ trong hệ thống.
 */
public class BusinessException extends RuntimeException {
    private String errorCode;
    private Object[] params;

    public BusinessException(String errorCode, Object[] params) {
        super(errorCode);
        this.errorCode = errorCode;
        this.params = params;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }
}
