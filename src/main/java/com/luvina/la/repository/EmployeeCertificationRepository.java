/**
 * Copyright 2024 Luvina Software Company
 * EmployeeCertificationRepository.java, 5/10/2024, HoanTV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.EmployeeCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EmployeeCertificationRepository chứa các phương thức tương tác với bảng EmployeeCertification trong database.
 * @Author HoanTV
 */
@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification, Long> {

    /**
     * Xóa danh sách chứng chỉ của nhân viên theo id nhân viên.
     * @param employeeId
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM employees_certifications WHERE employee_id = :employeeId", nativeQuery = true)
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);


}

