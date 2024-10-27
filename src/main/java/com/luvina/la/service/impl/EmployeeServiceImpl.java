/**
 * Copy right (C) 2024 Luvina company
 * EmployeeServiceImpl.java, 2024/10/17, HoanTV
 */
package com.luvina.la.service.impl;

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Certification;
import com.luvina.la.exception.BusinessException;
import com.luvina.la.payload.CertificationRequest;
import com.luvina.la.payload.EmployeeAddResponse;
import com.luvina.la.payload.EmployeeRequest;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.CertificationsRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.validation.EmployeeValidator;
import com.luvina.la.validation.CertificationValidator;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.dto.EmployeeDetailDTO.CertificationDTO;
import com.luvina.la.dto.ResponseDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EmployeeServiceImpl class để xử lý các nghiệp vụ liên quan đến nhân viên.
 * @Author: HoanTV
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Autowired
    private CertificationsRepository certificationsRepository;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private CertificationValidator certificationValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Thêm mới nhân viên
     * @param employeeRequest thông tin nhân viên cần thêm
     * @return thông báo kết quả thêm mới nhân viên
     */
    @Override
    @Transactional
    public ResponseDTO addEmployee(EmployeeRequest employeeRequest) {
        // 1. Kiểm tra departmentId có tồn tại
        Long departmentId = employeeRequest.getDepartmentId();
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (!departmentOptional.isPresent()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER004, new Object[]{"グループ"}));
        }
        Department department = departmentOptional.get();

        // 2. Tạo đối tượng Employee
        Employee employee = new Employee();
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());

        // Mã hóa mật khẩu trước khi lưu vào database
        String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword());
        employee.setEmployeeLoginPassword(encodedPassword);

        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeBirthDate(java.sql.Date.valueOf(employeeRequest.getEmployeeBirthDate()));  // Chuyển đổi String sang Date
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());
        employee.setDepartment(department);

        // 3. Lưu đối tượng Employee
        employee = employeeRepository.save(employee);

        // 4. Lưu danh sách chứng chỉ cho nhân viên nếu có
        List<CertificationRequest> certifications = employeeRequest.getCertifications();
        List<EmployeeCertification> employeeCertifications = new ArrayList<>();
        if (certifications != null) {
            for (CertificationRequest certReq : certifications) {
                if (isCertificationProvided(certReq)) {
                    // Validate chứng chỉ
                    ResponseDTO certValidation = certificationValidator.validateCertification(certReq);
                    if (certValidation != null) {
                        return certValidation;  // Trả về lỗi nếu có lỗi từ validation chứng chỉ
                    }

                    Optional<Certification> certificationOptional = certificationsRepository.findById(certReq.getCertificationId());
                    if (!certificationOptional.isPresent()) {
                        return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER004, new Object[]{"資格"}));
                    }
                    Certification certification = certificationOptional.get();

                    // Tạo đối tượng EmployeeCertification để lưu thông tin chứng chỉ
                    EmployeeCertification employeeCertification = new EmployeeCertification();
                    employeeCertification.setEmployee(employee);
                    employeeCertification.setCertification(certification);
                    employeeCertification.setStartDate(java.sql.Date.valueOf(certReq.getCertificationDate()));
                    employeeCertification.setEndDate(java.sql.Date.valueOf(certReq.getExpirationDate()));
                    employeeCertification.setScore(BigDecimal.valueOf(certReq.getScore()));
                    employeeCertifications.add(employeeCertification);
                }
            }
            if (!employeeCertifications.isEmpty()) {
                employeeCertificationRepository.saveAll(employeeCertifications);
            }
        }

        // 5. Chuẩn bị response trả về
        return new ResponseDTO(HttpStatus.OK.value(), employee.getEmployeeId(), new ResponseDTO.Message(Constants.MSG001, new Object[]{}));
    }

    /**
     * Chỉnh sửa thông tin nhân viên
     * @param employeeRequest thông tin nhân viên cần chỉnh sửa
     * @return thông báo kết quả chỉnh sửa nhân viên
     */
    @Override
    @Transactional
    public ResponseDTO editEmployee(EmployeeRequest employeeRequest) {
        // 1. Kiểm tra sự tồn tại của nhân viên
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeRequest.getEmployeeId());
        if (!optionalEmployee.isPresent()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER013, new Object[]{"ID"}));
        }
        Employee employee = optionalEmployee.get();

        // 2. Kiểm tra nếu employeeLoginId thay đổi và có trùng lặp với nhân viên khác
        if (!employee.getEmployeeLoginId().equals(employeeRequest.getEmployeeLoginId())) {
            if (employeeRepository.existsByEmployeeLoginIdAndEmployeeIdNot(employeeRequest.getEmployeeLoginId(), employee.getEmployeeId())) {
                return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER003, new Object[]{"アカウント名"}));
            }
        }

        // 3. Cập nhật thông tin nhân viên
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());
        if (employeeRequest.getEmployeeLoginPassword() != null && !employeeRequest.getEmployeeLoginPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword());
            employee.setEmployeeLoginPassword(encodedPassword);
        }
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeBirthDate(java.sql.Date.valueOf(employeeRequest.getEmployeeBirthDate()));
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());

        // 4. Cập nhật department
        Optional<Department> departmentOptional = departmentRepository.findById(employeeRequest.getDepartmentId());
        if (!departmentOptional.isPresent()) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER004, new Object[]{"グループ"}));
        }
        employee.setDepartment(departmentOptional.get());

        // 5. Cập nhật thông tin nhân viên
        employeeRepository.save(employee);

        // 6. Xóa chứng chỉ hiện có và thêm mới (nếu có)
        employeeCertificationRepository.deleteByEmployeeId(employee.getEmployeeId());
        List<EmployeeCertification> employeeCertifications = new ArrayList<>();
        if (employeeRequest.getCertifications() != null) {
            for (var certReq : employeeRequest.getCertifications()) {
                ResponseDTO certValidation = certificationValidator.validateCertification(certReq);
                if (certValidation != null) {
                    return certValidation;  // Trả về lỗi nếu có từ validate chứng chỉ
                }

                Optional<Certification> certificationOptional = certificationsRepository.findById(certReq.getCertificationId());
                if (!certificationOptional.isPresent()) {
                    return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ResponseDTO.Message(Constants.ER004, new Object[]{"資格"}));
                }
                EmployeeCertification employeeCertification = new EmployeeCertification();
                employeeCertification.setEmployee(employee);
                employeeCertification.setCertification(certificationOptional.get());
                employeeCertification.setStartDate(java.sql.Date.valueOf(certReq.getCertificationDate()));
                employeeCertification.setEndDate(java.sql.Date.valueOf(certReq.getExpirationDate()));
                employeeCertification.setScore(BigDecimal.valueOf(certReq.getScore()));
                employeeCertifications.add(employeeCertification);
            }
            employeeCertificationRepository.saveAll(employeeCertifications);
        }

        // 7. Trả về phản hồi thành công
        return new ResponseDTO(HttpStatus.OK.value(), employee.getEmployeeId(), new ResponseDTO.Message(Constants.MSG002, new Object[]{}));
    }


    /**
     * Kiểm tra xem thông tin chứng chỉ có được cung cấp không.
     * Nếu tất cả các trường của chứng chỉ đều rỗng hoặc null thì bỏ qua.
     */
    private boolean isCertificationProvided(CertificationRequest cert) {
        // Kiểm tra nếu có bất kỳ trường nào trong chứng chỉ được cung cấp
        return (cert.getCertificationId() != null && cert.getCertificationId() > 0)
                || (cert.getCertificationDate() != null && !cert.getCertificationDate().isEmpty())
                || (cert.getExpirationDate() != null && !cert.getExpirationDate().isEmpty())
                || (cert.getScore() != null && cert.getScore() > 0);
    }


    // Cập nhật để thêm các tham số order
    @Override
    @Transactional
    public Page<EmployeeDTO> getAllEmployees(String employeeName, Long departmentId,
                                             String ord_employee_name, String ord_certification_name,
                                             String ord_end_date, Pageable pageable) {
        // Gọi repository để lấy danh sách nhân viên với sắp xếp
        Page<Employee> employeePage = employeeRepository.findEmployeesWithDetails(employeeName, departmentId,
                ord_employee_name, ord_certification_name,
                ord_end_date, pageable);

        // Chuyển đổi từ entity sang DTO và trả về kết quả
        return employeePage.map(this::toEmployeeDTO);
    }

    // Chuyển đổi từ Employee entity sang EmployeeDTO
    private EmployeeDTO toEmployeeDTO(Employee employee) {
        // Tạo đối tượng EmployeeDTO
        EmployeeDTO dto = new EmployeeDTO();
        // Gán các giá trị từ entity sang DTO
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmployeeBirthDate(EmployeeDTO.formatDate(employee.getEmployeeBirthDate()));
        dto.setDepartmentName(employee.getDepartment().getDepartmentName());
        dto.setEmployeeEmail(employee.getEmployeeEmail());
        dto.setEmployeeTelephone(employee.getEmployeeTelephone());

        // Xử lý chứng chỉ (nếu có)
        if (employee.getEmployeeCertifications() != null && !employee.getEmployeeCertifications().isEmpty()) {
            dto.setCertificationName(employee.getEmployeeCertifications().get(0).getCertification().getCertificationName());
            dto.setEndDate(EmployeeDTO.formatDate(employee.getEmployeeCertifications().get(0).getEndDate()));
            dto.setScore(String.valueOf(employee.getEmployeeCertifications().get(0).getScore().intValue()));
        }
        return dto;
    }


    /**
     * Lấy thông tin chi tiết của nhân viên
     * @param employeeId
     * @return thông tin chi tiết của nhân viên
     */
    @Override
    @Transactional
    public EmployeeDetailDTO getEmployeeDetail(Long employeeId) {
        // 1. Lấy thông tin nhân viên
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        // Kiểm tra xem nhân viên có tồn tại không
        if (!employeeOptional.isPresent()) {
            throw new BusinessException(Constants.ER013, new Object[]{"ID"}); // Không tìm thấy nhân viên
        }
        Employee employee = employeeOptional.get();

        // 2. Lấy danh sách chứng chỉ của nhân viên
        List<EmployeeCertification> certifications = employee.getEmployeeCertifications();

        // 3. Tạo EmployeeDetailDTO và điền dữ liệu
        EmployeeDetailDTO detailDTO = new EmployeeDetailDTO();
        detailDTO.setCode(HttpStatus.OK.toString());
        detailDTO.setEmployeeId(employee.getEmployeeId());
        detailDTO.setEmployeeName(employee.getEmployeeName());
        detailDTO.setEmployeeBirthDate(employee.getEmployeeBirthDate());
        detailDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
        detailDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        detailDTO.setEmployeeEmail(employee.getEmployeeEmail());
        detailDTO.setEmployeeTelephone(employee.getEmployeeTelephone());
        detailDTO.setEmployeeNameKana(employee.getEmployeeNameKana());
        detailDTO.setEmployeeLoginId(employee.getEmployeeLoginId());

        // 4. Chuyển đổi danh sách chứng chỉ sang DTO
        List<CertificationDTO> certificationDTOs = certifications.stream()
                .map(cert -> {
                    CertificationDTO dto = new CertificationDTO();
                    dto.setCertificationId(cert.getCertification().getCertificationId());
                    dto.setCertificationName(cert.getCertification().getCertificationName());
                    dto.setStartDate(cert.getStartDate());
                    dto.setEndDate(cert.getEndDate());
                    dto.setScore(cert.getScore().intValue());
                    return dto;
                })
                .collect(Collectors.toList());

        detailDTO.setCertifications(certificationDTOs);

        return detailDTO;
    }

    /**
     * Xóa thông tin nhân viên
     * @param employeeId
     */
    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        // 1. Validate parameter employeeId
        if (employeeId == null) {
            throw new BusinessException(Constants.ER001, new Object[]{"ＩＤ"});
        }

        // Kiểm tra sự tồn tại của nhân viên
        if (!employeeRepository.existsById(employeeId)) {
            throw new BusinessException(Constants.ER014, new Object[]{"ＩＤ"});
        }

        // 2. Xóa thông tin chứng chỉ của nhân viên
        try {
            // Sử dụng truy vấn để xóa chứng chỉ dựa vào employeeId
            employeeCertificationRepository.deleteByEmployeeId(employeeId);

            // 3. Xóa thông tin của nhân viên
            employeeRepository.deleteById(employeeId);
        } catch (Exception e) {
            // Rollback transaction nếu có lỗi xảy ra và ném ra lỗi nghiệp vụ
            throw new BusinessException(Constants.ER015, new Object[]{});
        }
    }

}
