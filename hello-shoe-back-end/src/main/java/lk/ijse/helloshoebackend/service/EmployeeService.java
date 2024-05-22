package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public interface EmployeeService {
    Integer saveEmployee(EmployeeDTO employeeDTO);
    Integer updateEmployee(EmployeeDTO employeeDTO);
    Integer disable(String id);
    Integer enable(String id);
    EmployeeDTO searchEmployee(String id);
    List<EmployeeDTO> getAllEmployees();
}
