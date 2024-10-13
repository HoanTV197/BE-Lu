package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface EmployeeService chứa các phương thức xử lý nghiệp vụ liên quan đến Employee.
 * @Author: HoanTV
 */
public interface EmployeeService {
    // Cập nhật để thêm các tham số order
    Page<EmployeeDTO> getAllEmployees(String employeeName, Long departmentId,
                                      String ord_employee_name, String ord_certification_name,
                                      String ord_end_date, Pageable pageable);
}
