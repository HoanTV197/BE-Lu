/**
 * Copyright 2024 Luvina Softwave Company
 * DepartmentService.java, 5/10/2024, HoanTV
 */
package com.luvina.la.service;

import com.luvina.la.dto.DepartmentDTO;
import java.util.List;

/**
 * DepartmentService chứa các phương thức xử lý logic liên quan đến phòng ban.
 * @Author HoanTV
 */
public interface DepartmentService {
    // Lấy danh sách tất cả các phòng ban.
    List<DepartmentDTO> getAllDepartments();

}
