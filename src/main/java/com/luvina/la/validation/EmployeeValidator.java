/**
 * Copyright 2024 Luvina Software
 *  EmployeeValidator.java, 18/10/2024, HoanTV
 */
package com.luvina.la.validation;

import com.luvina.la.config.Constants;
import com.luvina.la.dto.ResponseDTO;
import com.luvina.la.payload.EmployeeRequest;
import com.luvina.la.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * EmployeeValidator class để validate các thông tin liên quan đến nhân viên.
 */
@Component
public class EmployeeValidator {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Phương thức validate các thông tin nhân viên khi thêm mới.
     * @param employeeRequest đối tượng EmployeeRequest chứa thông tin cần validate.
     * @return ResponseDTO nếu có lỗi, null nếu không có lỗi.
     */
    public ResponseDTO validateForAddEmployee(EmployeeRequest employeeRequest) {
        // 1. Validate employeeLoginId
        if (employeeRequest.getEmployeeLoginId() == null || employeeRequest.getEmployeeLoginId().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"アカウント名"}));
        }
        if (employeeRequest.getEmployeeLoginId().length() > 50) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER006, new Object[]{"アカウント名"}));
        }
        if (!employeeRequest.getEmployeeLoginId().matches("^[a-zA-Z0-9_]+$")) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER019, new Object[]{"アカウント名"}));
        }
        if (employeeRepository.existsByEmployeeLoginId(employeeRequest.getEmployeeLoginId())) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER003, new Object[]{"アカウント名"}));
        }

        // 2. Validate password (only for add)
        if (employeeRequest.getEmployeeLoginPassword() == null || employeeRequest.getEmployeeLoginPassword().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"パスワード"}));
        }
        if (employeeRequest.getEmployeeLoginPassword().length() < 8 || employeeRequest.getEmployeeLoginPassword().length() > 50) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER007, new Object[]{"パスワード", 8, 50}));
        }

        // Reuse common validation logic
        return validateCommonFields(employeeRequest);
    }

    /**
     * Phương thức validate các thông tin nhân viên khi chỉnh sửa.
     * @param employeeRequest đối tượng EmployeeRequest chứa thông tin cần validate.
     * @return ResponseDTO nếu có lỗi, null nếu không có lỗi.
     */
    public ResponseDTO validateForEditEmployee(EmployeeRequest employeeRequest) {
        // Không validate password khi chỉnh sửa
        return validateCommonFields(employeeRequest);
    }

    // Phương thức chung cho cả add và edit
    private ResponseDTO validateCommonFields(EmployeeRequest employeeRequest) {
        // 1. Validate employeeName
        if (employeeRequest.getEmployeeName() == null || employeeRequest.getEmployeeName().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"氏名"}));
        }
        if (employeeRequest.getEmployeeName().length() > 125) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER006, new Object[]{"氏名"}));
        }

        // 2. Validate employeeNameKana
        if (employeeRequest.getEmployeeNameKana() == null || employeeRequest.getEmployeeNameKana().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"カタカナ氏名"}));
        }
        if (employeeRequest.getEmployeeNameKana().length() > 125) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER006, new Object[]{"カタカナ氏名"}));
        }
        if (!employeeRequest.getEmployeeNameKana().matches("^[\\uFF65-\\uFF9F]+$")) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER009, new Object[]{"カタカナ氏名"}));
        }

        // 3. Validate employeeBirthDate
        if (employeeRequest.getEmployeeBirthDate() == null || employeeRequest.getEmployeeBirthDate().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"生年月日"}));
        }
        if (!employeeRequest.getEmployeeBirthDate().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER005, new Object[]{"生年月日", "yyyy/MM/dd"}));
        }
        if (!isValidDate(employeeRequest.getEmployeeBirthDate())) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER011, new Object[]{"生年月日"}));
        }

        // 4. Validate employeeEmail
        if (employeeRequest.getEmployeeEmail() == null || employeeRequest.getEmployeeEmail().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"メールアドレス"}));
        }
        if (employeeRequest.getEmployeeEmail().length() > 125) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER006, new Object[]{"メールアドレス"}));
        }

        // 5. Validate employeeTelephone
        if (employeeRequest.getEmployeeTelephone() == null || employeeRequest.getEmployeeTelephone().isEmpty()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER001, new Object[]{"電話番号"}));
        }
        if (employeeRequest.getEmployeeTelephone().length() > 50) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER006, new Object[]{"電話番号"}));
        }
        if (!employeeRequest.getEmployeeTelephone().matches("^[\\p{ASCII}]+$")) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER008, new Object[]{"電話番号"}));
        }

        // 6. Validate departmentId
        if (employeeRequest.getDepartmentId() == null) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER002, new Object[]{"グループ"}));
        }
        if (employeeRequest.getDepartmentId() <= 0) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    new ResponseDTO.Message(Constants.ER018, new Object[]{"グループ"}));
        }

        return null; // No validation errors
    }

    // Phương thức kiểm tra ngày hợp lệ
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
