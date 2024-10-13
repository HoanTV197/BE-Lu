package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 6868189362900231672L;

    private Long employeeId;
    private String employeeName;

    // Định dạng ngày sinh đúng chuẩn yyyy/MM/dd
    private String employeeBirthDate;
    private String departmentName;

    private String employeeEmail;
    private String employeeTelephone;
    private String certificationName;

    // Định dạng ngày hết hạn đúng chuẩn yyyy/MM/dd
    private String endDate;

    // Điểm chứng chỉ trả về dạng số nguyên
    private String score;

    // Method chuyển đổi Date thành String đúng format
    public static String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}
