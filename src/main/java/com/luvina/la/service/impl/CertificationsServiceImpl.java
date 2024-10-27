/**
 * Copyright 2024 Luvina Software
 * CertificationsServiceImpl.java, 5/10/2024, HoanTV
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.repository.CertificationsRepository;
import com.luvina.la.service.CertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * CertificationsServiceImpl chứa các phương thức xử lý logic liên quan đến certifications.
 * @author HoanTV
 */
@Service
public class CertificationsServiceImpl implements CertificationsService {

    private final CertificationsRepository certificationsRepository;

    // Inject CertificationsRepository vào CertificationsServiceImpl để sử dụng các phương thức của CertificationsRepository
    @Autowired
    public CertificationsServiceImpl(CertificationsRepository certificationsRepository) {
        this.certificationsRepository = certificationsRepository;
    }

    /**
     * Lấy danh sách tất cả các certifications.
     * @return Danh sách certifications.
     */
    @Override
    public List<CertificationDTO> getAllCertifications() {
        /**
         * Gọi phương thức findAllCertificationsNative() của CertificationsRepository để lấy danh sách certifications.
         */
        List<Object[]> results = certificationsRepository.findAllCertificationsNative();
        List<CertificationDTO> certifications = new ArrayList<>();

        // Ánh xạ từ Object[] sang CertificationDTO
        for (Object[] result : results) {
            CertificationDTO dto = new CertificationDTO(
                    ((Number) result[0]).longValue(),  // certificationId
                    (String) result[1]                 // certificationName
            );
            certifications.add(dto);
        }

        return certifications;
    }
}
