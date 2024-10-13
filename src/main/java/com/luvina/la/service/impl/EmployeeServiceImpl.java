package com.luvina.la.service.impl;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Phải khớp với khai báo trong interface
    @Override
    public Page<EmployeeDTO> getAllEmployees(String employeeName, Long departmentId,
                                             String ord_employee_name, String ord_certification_name,
                                             String ord_end_date, Pageable pageable) {
        // Gọi repository để lấy danh sách nhân viên với sắp xếp
        Page<Employee> employeePage = employeeRepository.findEmployeesWithDetails(employeeName, departmentId,
                ord_employee_name, ord_certification_name,
                ord_end_date, pageable);

        // Chuyển đổi từ entity sang DTO và trả về kết quả
        return employeePage.map(this::toEmployeeDTO);
    }

    // Chuyển đổi từ Employee entity sang EmployeeDTO
    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmployeeBirthDate(EmployeeDTO.formatDate(employee.getEmployeeBirthDate()));
        dto.setDepartmentName(employee.getDepartment().getDepartmentName());
        dto.setEmployeeEmail(employee.getEmployeeEmail());
        dto.setEmployeeTelephone(employee.getEmployeeTelephone());

        // Xử lý chứng chỉ (nếu có)
        if (employee.getEmployeeCertifications() != null && !employee.getEmployeeCertifications().isEmpty()) {
            dto.setCertificationName(employee.getEmployeeCertifications().get(0).getCertification().getCertificationName());
            dto.setEndDate(EmployeeDTO.formatDate(employee.getEmployeeCertifications().get(0).getEndDate()));
            dto.setScore(String.valueOf(employee.getEmployeeCertifications().get(0).getScore()));
        }
        return dto;
    }
}
