/**
 * Copy right (C) 2024 Luvina Software Company
 * DepartmentController.java, 5/10/2024, HoanTV
 */

package com.luvina.la.controller;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.payload.DepartmentResponse;
import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.payload.ErrorResponse;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller quản lý các thao tác liên quan đến phòng ban.
 * Xử lý các yêu cầu HTTP liên quan đến việc lấy dữ liệu phòng ban.
 *
 * @author HoanTV
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    // Inject đối tượng service cho phòng ban
    @Autowired
    private DepartmentService departmentService;

    /**
     * Lấy danh sách tất cả các phòng ban.
     * Phương thức này sẽ lấy danh sách phòng ban từ service và trả về dưới dạng JSON response.
     *
     * @return ResponseEntity chứa danh sách phòng ban hoặc thông báo lỗi.
     */
    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        try {
            // Lấy danh sách phòng ban từ service
            List<DepartmentDTO> departmentList = departmentService.getAllDepartments();

            // Tạo response thành công với mã trạng thái 200
            DepartmentResponse response = new DepartmentResponse(HttpStatus.OK.toString(), departmentList);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // In ra ngoại lệ
            e.printStackTrace();

            // Tạo thông báo lỗi sử dụng hằng số từ class Constants và trả về mã lỗi 500
            ErrorMessage errorMessage = new ErrorMessage(Constants.ER023, new Object[]{});
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorMessage);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
