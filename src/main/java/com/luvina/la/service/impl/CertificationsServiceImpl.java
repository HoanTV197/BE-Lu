package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.repository.CertificationsRepository;
import com.luvina.la.service.CertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificationsServiceImpl implements CertificationsService {

    private final CertificationsRepository certificationsRepository;

    @Autowired
    public CertificationsServiceImpl(CertificationsRepository certificationsRepository) {
        this.certificationsRepository = certificationsRepository;
    }

    @Override
    public List<CertificationDTO> getAllCertifications() {
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
