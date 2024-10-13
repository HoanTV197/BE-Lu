/**
 * Copy right (C) 2024 Luvina Software Company
 * EmployeeResponse.java, 5/10/2024, HoanTV
 */
package com.luvina.la.payload;

import com.luvina.la.dto.EmployeeDTO;
import lombok.Data;

import java.util.List;

/**
 * EmployeeResponse chứa thông tin trả về sau khi lấy danh sách nhân viên.
 * @Author: HoanTV
 */
@Data
public class EmployeeResponse {
    private String code;
    private int totalRecords;
    private List<EmployeeDTO> employees;
}