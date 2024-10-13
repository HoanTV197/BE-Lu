/**
 * Copyright(C) 2024 Luvina Company
 * Department.java, 03/10/2024 Created by [Tạ Văn Hoan]
 */
package com.luvina.la.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity class đại diện cho bảng Departments trong database.
 * Lưu trữ thông tin về phòng ban trong công ty.
 *
 * @author HoanTV
 */
@Entity
@Table(name = "departments")
@Data
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của phòng ban (Primary Key).
     */
    @Id
    @Column(name = "department_id", unique = true)
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    @Column(name = "department_name", nullable = false)
    private String departmentName;
}
