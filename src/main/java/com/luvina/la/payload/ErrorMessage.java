package com.luvina.la.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorMessage chứa thông tin lỗi trả về.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
    private String code;
    private Object[] params;
}
