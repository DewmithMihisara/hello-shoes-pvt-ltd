package lk.ijse.helloshoebackend.util;

import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class IdService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public static String generateID(Constants constants) {
        switch (constants){
            case EMPLOYEE_ID:
                return "EMP-" + System.currentTimeMillis();
            case USER_ID:
                return "USR-" + System.currentTimeMillis();
            default:
                return "EMP-" + System.currentTimeMillis();
        }
    }
}
