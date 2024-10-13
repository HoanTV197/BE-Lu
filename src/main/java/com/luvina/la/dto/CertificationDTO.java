package com.luvina.la.dto;

public class CertificationDTO {
    private Long certificationId;
    private String certificationName;

    // Constructors
    public CertificationDTO() {}

    public CertificationDTO(Long certificationId, String certificationName) {
        this.certificationId = certificationId;
        this.certificationName = certificationName;
    }

    // Getters and Setters
    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }
}
