/**
 * Copyright 2024 Luvina Softwave Company
 * CertificationsService.java, 5/10/2024, HoanTV
 */
package com.luvina.la.service;

import com.luvina.la.dto.CertificationDTO;
import java.util.List;

/**
 * CertificationsService chứa các phương thức xử lý logic liên quan đến certifications.
 * @Author HoanTV
 */
public interface CertificationsService {
    // Lấy danh sách tất cả các certifications.
    List<CertificationDTO> getAllCertifications();
}
