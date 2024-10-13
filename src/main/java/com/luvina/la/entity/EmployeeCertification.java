/**
 * Copyright(C) 2024 Luvina Company
 * EmployeeCertification.java, 03/10/2024 Created by [Tạ Văn Hoan]
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 * Entity class đại diện cho bảng employees_certifications trong database.
 * Liên kết giữa nhân viên và các chứng chỉ mà họ đã đạt được.
 *
 * @author HoanTV
 */
@Entity
@Table(name = "employees_certifications")
@Data
public class EmployeeCertification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của quan hệ giữa nhân viên và chứng chỉ (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id", unique = true)
    private Long employeeCertificationId;

    /**
     * Nhân viên liên kết với chứng chỉ (Foreign Key).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * Chứng chỉ liên kết với nhân viên (Foreign Key).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id", nullable = false)
    private Certification certification;

    /**
     * Ngày bắt đầu khi nhân viên nhận được chứng chỉ.
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * Ngày kết thúc hoặc ngày hết hạn chứng chỉ.
     */
    @Column(name = "end_date")
    private Date endDate;


    /**
     * Điểm số đạt được (nếu có) khi hoàn thành chứng chỉ.
     */
    @Column(name = "score")
    private BigDecimal score;
}
