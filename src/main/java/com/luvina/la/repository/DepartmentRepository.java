package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository chứa các phương thức tương tác với bảng Department trong database.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
