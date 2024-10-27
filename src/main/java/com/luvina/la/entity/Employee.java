/**
 * Copyright(C) 2024 Luvina Company
 * Employee.java, 03/10/2024 Created by [Tạ Văn Hoan]
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

/**
 * Entity class đại diện cho bảng employees trong database.
 * Lưu trữ thông tin về nhân viên như tên, email, phòng ban, ngày sinh,...
 *
 * @author HoanTV
 */
@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 5771173953267484096L;

    /**
     * ID của nhân viên (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng auto-increment
    @Column(name = "employee_id", unique = true, nullable = false)
    private Long employeeId;

    /**
     * Phòng ban mà nhân viên thuộc về (Foreign Key).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /**
     * Tên của nhân viên.
     */
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    /**
     * Tên của nhân viên theo kiểu Kana (chữ Nhật).
     */
    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    /**
     * Ngày sinh của nhân viên.
     */
    @Column(name = "employee_birth_date")
    private Date employeeBirthDate;

    /**
     * Địa chỉ email của nhân viên.
     */
    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    /**
     * Số điện thoại của nhân viên.
     */
    @Column(name = "employee_telephone")
    private String employeeTelephone;

    /**
     * ID đăng nhập của nhân viên.
     */
    @Column(name = "employee_login_id", nullable = false)
    private String employeeLoginId;

    /**
     * Mật khẩu đăng nhập của nhân viên.
     */
    @Column(name = "employee_login_password", nullable = false)
    private String employeeLoginPassword;

    /**
     * Danh sách chứng chỉ của nhân viên.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeCertification> employeeCertifications;


}
