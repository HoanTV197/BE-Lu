package com.luvina.la.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO dành riêng cho API lấy chi tiết thông tin nhân viên.
 */
@Data
public class EmployeeDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private Long employeeId;
    private String employeeName;

    // Định dạng ngày sinh theo chuẩn yyyy/MM/dd
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;

    private Long departmentId;
    private String departmentName;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginId;

    // Danh sách các chứng chỉ của nhân viên
    private List<CertificationDTO> certifications;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // DTO cho thông tin chi tiết của chứng chỉ
    @Data
    public static class CertificationDTO {
        private Long certificationId;
        private String certificationName;

        // Định dạng ngày bắt đầu và kết thúc chứng chỉ theo chuẩn yyyy/MM/dd
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date endDate;

        private int score; // Điểm chứng chỉ trả về dưới dạng số nguyên
    }
}
