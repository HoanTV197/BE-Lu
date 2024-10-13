package com.luvina.la.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CertificationsResponse là phản hồi cho danh sách certifications.
 */
@Data
@AllArgsConstructor
public class CertificationsResponse<T> {
    private String code;
    private T certifications;
}
