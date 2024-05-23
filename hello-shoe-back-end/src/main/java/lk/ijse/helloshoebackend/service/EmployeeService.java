package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface EmployeeService {
    boolean saveEmployee(EmployeeDTO employee, MultipartFile file) throws IOException;

    List<EmployeeDTO> getAllAdmins();

    List<EmployeeDTO> getAllCashiers();

    EmployeeDTO getEmployee(String empId);

    boolean updateEmployee(EmployeeDTO employeeDTO,MultipartFile file) throws IOException;
}
