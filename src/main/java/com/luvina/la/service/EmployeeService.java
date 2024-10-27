/**
 * Copyright 2024 Luvina Software Company
 * EmployeeService.java, 5/10/2024, HoanTV
 */
package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.dto.ResponseDTO;
import com.luvina.la.payload.EmployeeAddResponse;
import com.luvina.la.payload.EmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Interface EmployeeService chứa các phương thức xử lý nghiệp vụ liên quan đến Employee.
 * @Author: HoanTV
 */
public interface EmployeeService {
    // Thêm mới nhân viên
    ResponseDTO addEmployee(EmployeeRequest employeeRequest);

    // Thêm hoặc chỉnh sửa thông tin nhân viên
    ResponseDTO editEmployee(EmployeeRequest employeeRequest);

    // Cập nhật để thêm các tham số order
    Page<EmployeeDTO> getAllEmployees(String employeeName, Long departmentId,
                                      String ord_employee_name, String ord_certification_name,
                                      String ord_end_date, Pageable pageable);

    // Lấy thông tin chi tiết nhân viên
    EmployeeDetailDTO getEmployeeDetail(Long employeeId);

    // Khai báo phương thức xóa nhân viên
    void deleteEmployee(Long employeeId);
}
