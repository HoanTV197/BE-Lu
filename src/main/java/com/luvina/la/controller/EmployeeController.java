/**
 * Copy right (C) 2024 Luvina Software Company
 * EmployeeController.java, 18/10/2024, HoanTV
 */
package com.luvina.la.controller;

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.ResponseDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.exception.BusinessException;
import com.luvina.la.payload.*;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.validation.EmployeeValidator;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EmployeeValidator employeeValidator;

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



    /**
     * API thêm mới nhân viên.
     * @param employeeRequest Thông tin nhân viên cần thêm
     * @return Response chứa thông báo thành công hoặc lỗi
     */
    @PostMapping("/employees/add")
    public ResponseEntity<ResponseDTO> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        // Validate thông tin nhân viên
        ResponseDTO validationResponse = employeeValidator.validateForAddEmployee(employeeRequest);
        if (validationResponse != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validationResponse);
        }

        // Gọi service để thêm mới nhân viên và nhận về ResponseDTO
        ResponseDTO response = employeeService.addEmployee(employeeRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }


    /**
     * API chỉnh sửa thông tin nhân viên.
     * @param employeeRequest Thông tin nhân viên cần chỉnh sửa
     * @return ResponseEntity với kết quả trả về
     */
    @PutMapping("/employees/edit")
    public ResponseEntity<ResponseDTO> editEmployee(@RequestBody EmployeeRequest employeeRequest) {
        // Validate thông tin nhân viên khi chỉnh sửa
        ResponseDTO validationResponse = employeeValidator.validateForEditEmployee(employeeRequest);
        if (validationResponse != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validationResponse);
        }

        // Gọi service để chỉnh sửa nhân viên
        ResponseDTO response = employeeService.editEmployee(employeeRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }



    /**
     * API để lấy thông tin chi tiết của nhân viên theo ID.
     *
     * @param employeeId ID của nhân viên
     * @return ResponseEntity chứa thông tin nhân viên hoặc thông tin lỗi
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeDetail(@PathVariable("id") Long employeeId) {
        try {
            EmployeeDetailDTO employeeDetail = employeeService.getEmployeeDetail(employeeId);
            return ResponseEntity.ok(employeeDetail);
        } catch (BusinessException ex) {
            // Xử lý lỗi và trả về response với thông tin lỗi
            ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode(), ex.getParams());
            ErrorResponse errorResponse = new ErrorResponse("500", errorMessage);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * API để xóa nhân viên theo ID.
     * @param employeeId
     * @return ResponseEntity chứa thông tin xóa nhân viên hoặc thông tin lỗi
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long employeeId) {
        try {
            // Gọi service để xóa nhân viên
            employeeService.deleteEmployee(employeeId);

            // Chuẩn bị response trả về khi thành công
            EmployeeDeleteResponse.Message message = new EmployeeDeleteResponse.Message("MSG003", new Object[]{});
            EmployeeDeleteResponse response = new EmployeeDeleteResponse();
            response.setCode(HttpStatus.OK.value());
            response.setEmployeeId(employeeId);
            response.setMessage(message);
            return ResponseEntity.ok(response);
        } catch (BusinessException ex) {
            // Xử lý lỗi và trả về response với thông tin lỗi
            ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode(), ex.getParams());
            ErrorDeleteResponse errorResponse = new ErrorDeleteResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),employeeId,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }





    /**
     * Kiểm tra xem tham số order có hợp lệ không.
     * @param orderValue
     * @return true nếu hợp lệ, ngược lại trả về false
     */
    private boolean isValidOrder(String orderValue) {
        List<String> validOrders = Arrays.asList("ASC", "DESC");
        return orderValue == null || validOrders.contains(orderValue.toUpperCase());
    }

    /**
     * Tạo response lỗi từ mã lỗi và tham số.
     * @param errorCode mã lỗi
     * @param param
     * @return ResponseEntity chứa thông tin lỗi
     */
    private ResponseEntity<ErrorResponse> createErrorResponse(String errorCode, String param) {
        ErrorMessage errorMessage = new ErrorMessage(errorCode, new Object[]{param});
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
