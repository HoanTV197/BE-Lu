package com.luvina.la.validation;

import com.luvina.la.config.Constants;
import com.luvina.la.payload.CertificationRequest;
import com.luvina.la.dto.ResponseDTO;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CertificationValidator {

    /**
     * Phương thức validate các thông tin chứng chỉ.
     * @param cert đối tượng CertificationRequest chứa thông tin cần validate.
     * @return ResponseDTO với thông tin lỗi nếu có, null nếu hợp lệ.
     */
    public ResponseDTO validateCertification(CertificationRequest cert) {
        if (isCertificationProvided(cert)) {
            return validateFields(cert);
        }
        return null; // Không có chứng chỉ, bỏ qua
    }

    /**
     * Kiểm tra xem thông tin chứng chỉ có được cung cấp không.
     */
    private boolean isCertificationProvided(CertificationRequest cert) {
        return !(isEmpty(String.valueOf(cert.getCertificationId())) && isEmpty(cert.getCertificationDate())
                && isEmpty(cert.getExpirationDate()) && isEmpty(String.valueOf(cert.getScore())));
    }

    /**
     * Validate các trường của chứng chỉ nếu chứng chỉ có mặt.
     */
    private ResponseDTO validateFields(CertificationRequest cert) {
        // 1. Validate certificationDate
        if (isEmpty(cert.getCertificationDate())) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER001, new Object[]{"資格交付日"}));
        }
        if (!cert.getCertificationDate().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER005, new Object[]{"資格交付日", "yyyy-MM-dd"}));
        }
        if (!isValidDate(cert.getCertificationDate())) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER011, new Object[]{"資格交付日"}));
        }

        // 2. Validate expirationDate
        if (isEmpty(cert.getExpirationDate())) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER001, new Object[]{"失効日"}));
        }
        if (!cert.getExpirationDate().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER005, new Object[]{"失効日", "yyyy-MM-dd"}));
        }
        if (!isValidDate(cert.getExpirationDate())) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER011, new Object[]{"失効日"}));
        }
        if (cert.getExpirationDate().compareTo(cert.getCertificationDate()) < 0) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER012, new Object[]{"失効日"}));
        }

        // 3. Validate score
        if (cert.getScore() == null || cert.getScore() <= 0) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER018, new Object[]{"点数"}));
        }

        // 4. Validate certificationId
        if (cert.getCertificationId() == null || cert.getCertificationId() <= 0) {
            return new ResponseDTO(500, null, new ResponseDTO.Message(Constants.ER001, new Object[]{"資格"}));
        }

        return null; // Nếu không có lỗi
    }

    /**
     * Phương thức kiểm tra ngày hợp lệ.
     */
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

    /**
     * Kiểm tra xem chuỗi có rỗng hay không.
     */
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
