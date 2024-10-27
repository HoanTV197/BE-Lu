/**
 * Copyright (C) 2024 Luvina Software Company
 * DepartmentRepository.java, 5/10/2024, HoanTV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository chứa các phương thức tương tác với bảng Department trong database.
 * @Author HoanTV
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
