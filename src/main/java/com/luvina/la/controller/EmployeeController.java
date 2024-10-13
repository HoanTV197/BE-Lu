/**
 * Copy right (C) 2024 Luvina Software Company
 * EmployeeController.java, 5/10/2024, HoanTV
 */
package com.luvina.la.controller;

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.payload.EmployeeResponse;
import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.payload.ErrorResponse;
import com.luvina.la.service.EmployeeService;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * EmployeeController chứa các API liên quan đến nhân viên.
 *
 * @Author: HoanTV
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * API lấy danh sách nhân viên với các tham số tùy chọn.
     * @param employeeName Tên nhân viên
     * @param departmentId Id phòng ban
     * @param ord_employee_name Sắp xếp theo tên nhân viên
     * @param ord_certification_name Sắp xếp theo tên chứng chỉ
     * @param ord_end_date Sắp xếp theo ngày kết thúc chứng chỉ
     * @param offset bắt đầu lấy từ bản ghi thứ mấy
     * @param limit  số lượng bản ghi lấy ra
     * @return
     */
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String ord_employee_name,
            @RequestParam(required = false) String ord_certification_name,
            @RequestParam(required = false) String ord_end_date,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit) {

        // 1. Validate order parameters
        if (!isValidOrder(ord_employee_name)) {
            return createErrorResponse(Constants.ER021, "ord_employee_name");
        }
        if (!isValidOrder(ord_certification_name)) {
            return createErrorResponse(Constants.ER021, "ord_certification_name");
        }
        if (!isValidOrder(ord_end_date)) {
            return createErrorResponse(Constants.ER021, "ord_end_date");
        }

        // 2. Validate offset and limit
        if (offset < 0) {
            return createErrorResponse(Constants.ER018, "オフセット");
        }
        if (limit <= 0) {
            return createErrorResponse(Constants.ER018, "リミット");
        }

        // Tạo đối tượng Pageable từ offset và limit
        Pageable pageable = PageRequest.of(offset / limit, limit);

        // Gọi service để lấy danh sách nhân viên đã chuyển đổi sang DTO
        Page<EmployeeDTO> employeePage = employeeService.getAllEmployees(employeeName, departmentId,
                ord_employee_name, ord_certification_name,
                ord_end_date, pageable);

        // Chuẩn bị response trả về
        EmployeeResponse response = new EmployeeResponse();
        response.setCode(HttpStatus.OK.toString());
        response.setTotalRecords((int) employeePage.getTotalElements());
        response.setEmployees(employeePage.getContent());

        return ResponseEntity.ok(response);
    }

    // Phương thức kiểm tra tính hợp lệ của order
    private boolean isValidOrder(String orderValue) {
        List<String> validOrders = Arrays.asList("ASC", "DESC");
        return orderValue == null || validOrders.contains(orderValue.toUpperCase());
    }

    // Tạo response khi gặp lỗi
    private ResponseEntity<ErrorResponse> createErrorResponse(String errorCode, String param) {
        ErrorMessage errorMessage = new ErrorMessage(errorCode, new Object[]{param});
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
