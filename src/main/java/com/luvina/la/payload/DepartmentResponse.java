package com.luvina.la.payload;

import com.luvina.la.dto.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * DepartmentResponse chứa thông tin trả về của phòng ban.
 */
@Data
@AllArgsConstructor
public class DepartmentResponse {
    private String code;
    private List<DepartmentDTO> departments;
}
