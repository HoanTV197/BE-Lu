/**
 * Copy right (C) 2024 Luvina Software Company
 * EmployeeRepository.java, 5/10/2024, HoanTV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * EmployeeRepository chứa các phương thức tương tác với bảng Employees trong database.'
 * @Author HoanTV
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Tìm kiếm nhân viên theo tên nhân viên và id phòng ban.
     * @param employeeName
     * @param departmentId
     * @param ord_employee_name
     * @param ord_certification_name
     * @param ord_end_date
     * @param pageable phân trang
     * @return Danh sách nhân viên
     */
    @Query(value = "SELECT e.* FROM employees e " +
            "INNER JOIN departments d ON e.department_id = d.department_id " +
            "LEFT JOIN employees_certifications ec ON e.employee_id = ec.employee_id " +
            "LEFT JOIN certifications c ON ec.certification_id = c.certification_id " +
            "WHERE (:employeeName IS NULL OR e.employee_name LIKE %:employeeName%) " +
            "AND (:departmentId IS NULL OR e.department_id = :departmentId) " +
            "ORDER BY " +
            "  COALESCE(NULLIF(:ord_employee_name, ''), 'ASC') = 'ASC', e.employee_name ASC, " +
            "  COALESCE(NULLIF(:ord_certification_name, ''), 'ASC') = 'ASC', c.certification_name ASC, " +
            "  COALESCE(NULLIF(:ord_end_date, ''), 'ASC') = 'ASC', ec.end_date ASC",
            countQuery = "SELECT COUNT(e.employee_id) FROM employees e " +
                    "INNER JOIN departments d ON e.department_id = d.department_id " +
                    "LEFT JOIN employees_certifications ec ON e.employee_id = ec.employee_id " +
                    "LEFT JOIN certifications c ON ec.certification_id = c.certification_id " +
                    "WHERE (:employeeName IS NULL OR e.employee_name LIKE %:employeeName%) " +
                    "AND (:departmentId IS NULL OR e.department_id = :departmentId)",
            nativeQuery = true)
    Page<Employee> findEmployeesWithDetails(@Param("employeeName") String employeeName,
                                            @Param("departmentId") Long departmentId,
                                            @Param("ord_employee_name") String ord_employee_name,
                                            @Param("ord_certification_name") String ord_certification_name,
                                            @Param("ord_end_date") String ord_end_date,
                                            Pageable pageable);

    /**
     * Tìm kiếm nhân viên theo id nhân viên.
     * @param username
     * @return Nhân viên tìm được
     */
    Optional<Employee> findByEmployeeLoginId(String username);

    /**
     * Kiểm tra sự tồn tại của nhân viên theo id nhân viên.
     * @param employeeLoginId id nhân viên
     * @return  true nếu tồn tại, ngược lại trả về false
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);

    // Kiểm tra xem employeeLoginId có trùng với employeeId khác hay không
    boolean existsByEmployeeLoginIdAndEmployeeIdNot(String employeeLoginId, Long employeeId);

    /**
     * Tìm kiếm nhân viên theo id nhân viên và join với bảng Department.
     * @param employeeId
     * @return Nhân viên tìm được
     */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.employeeId = :employeeId")
    Optional<Employee> findEmployeeWithDepartmentById(@Param("employeeId") Long employeeId);

    // Kiểm tra sự tồn tại của nhân viên theo id nhân viên
    boolean existsById(Long employeeId);

}
