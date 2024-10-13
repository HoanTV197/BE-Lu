/**
 * Copy right (C) 2024 Luvina Software Company
 * DepartmentServiceImpl.java, 5/10/2024, HoanTV
 */

package com.luvina.la.service.impl;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DepartmentServiceImpl chứa các phương thức xử lý logic liên quan đến phòng ban.
 * @Author HoanTV
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Lấy danh sách tất cả các phòng ban.
     * @return  Danh sách phòng ban.
     */
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        // Chuyển đổi Entity thành DTO
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentDTO(department.getDepartmentId(), department.getDepartmentName()))
                .collect(Collectors.toList());
    }
}
