/**
 * Copyright(C) 2024 Luvina Company
 * Certification.java, 03/10/2024 Created by [Tạ Văn Hoan]
 */
package com.luvina.la.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class đại diện cho bảng certifications trong database.
 * Lưu trữ thông tin về các chứng chỉ mà nhân viên có thể đạt được.
 *
 * @author HoanTV
 */
@Entity
@Table(name = "certifications")
@Data
public class Certification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của chứng chỉ (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id", unique = true)
    private Long certificationId;

    /**
     * Tên của chứng chỉ.
     */
    @Column(name = "certification_name", nullable = false)
    private String certificationName;

    /**
     * Cấp độ của chứng chỉ.
     */
    @Column(name = "certification_level", nullable = false)
    private Integer certificationLevel;
}
