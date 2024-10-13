package com.luvina.la.controller;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.payload.CertificationsResponse;
import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.payload.ErrorResponse;
import com.luvina.la.service.CertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certifications")
public class CertificationsController {

    private final CertificationsService certificationsService;

    @Autowired
    public CertificationsController(CertificationsService certificationsService) {
        this.certificationsService = certificationsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCertifications() {
        try {
            // Lấy danh sách certifications từ service
            List<CertificationDTO> certifications = certificationsService.getAllCertifications();

            // Tạo response cho trường hợp thành công
            CertificationsResponse<List<CertificationDTO>> response = new CertificationsResponse<>(HttpStatus.OK.toString(), certifications);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Tạo thông tin chi tiết lỗi
            ErrorMessage errorMessage = new ErrorMessage("ER023", new Object[]{});
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorMessage);

            // Trả về response lỗi nếu có exception xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
