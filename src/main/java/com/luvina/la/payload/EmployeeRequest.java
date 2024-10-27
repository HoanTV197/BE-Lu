package com.luvina.la.payload;

import lombok.Data;

import java.util.List;

/**
 * EmployeeRequest chứa thông tin cần thiết để thêm mới nhân viên.
 */
@Data
public class EmployeeRequest {
    private Long employeeId;
    private String employeeLoginId;
    private String employeeLoginPassword;
    private String employeeName;
    private String employeeNameKana;
    private String employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private Long departmentId;
    private List<CertificationRequest> certifications;
}
