package com.luvina.la.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorDeleteResponse chứa thông tin lỗi khi xóa nhân viên thất bại.
 */
@Data
@AllArgsConstructor
public class ErrorDeleteResponse {
    private String code;
    private Long employeeId;
    private ErrorMessage message;
}
