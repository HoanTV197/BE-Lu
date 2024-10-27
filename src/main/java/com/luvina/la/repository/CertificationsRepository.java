/**
 * Copyright 2024 Luvina Software Company
 * CertificationsRepository.java, 5/10/2024, HoanTV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * CertificationsRepository chứa các phương thức tương tác với bảng certifications trong database.
 * @Author HoanTV
 */
@Repository
public interface CertificationsRepository extends JpaRepository<Certification, Long> {

    /**
     * Lấy danh sách tất cả các chứng chỉ.
     * @return Danh sách chứng chỉ.
     */
    @Query(value = "SELECT c.certification_id AS certificationId, " +
                    "c.certification_name AS certificationName "    +
                        "FROM certifications c", nativeQuery = true)
    List<Object[]> findAllCertificationsNative();
}
