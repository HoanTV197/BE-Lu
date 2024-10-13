/**
 * Copy right (C) 2024 Luvina Software Company
 * DepartmentDTO.java, Aug 11, 2024, HoanTV
 */
package com.luvina.la.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Lớp DepartmentDTO chứa thông tin của phòng ban.
 */
@Data //  Tự động tạo tất cả các getter, setter, toString(), equals(), và hashCode()
@AllArgsConstructor //Tạo constructor không có tham số.
@NoArgsConstructor //Tạo constructor với đầy đủ tham số.
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
}
