package com.luvina.la.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO class đại diện cho EmployeeCertification entity.
 */
@Data
public class EmployeeCertificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String certificationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    private int score;  // Điểm số định dạng là số nguyên
}
