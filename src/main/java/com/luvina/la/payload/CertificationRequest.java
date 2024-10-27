package com.luvina.la.payload;

import lombok.Data;

/**
 * CertificationRequest class chứa các thông tin cần thiết để thêm mới hoặc cập nhật certification.
 */
@Data
public class CertificationRequest {
    private Long certificationId;
    private String certificationDate;
    private String expirationDate;
    private Integer score;
}
