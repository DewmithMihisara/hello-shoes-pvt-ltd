package lk.ijse.helloshoebackend.service.Impl;

import lk.ijse.helloshoebackend.dto.UserDTO;
import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.UserService;
import lk.ijse.helloshoebackend.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class UserServiceIMPL implements UserService {
    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserServiceIMPL(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Integer updateUsr(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getId())) {
            userRepository.findById(userDTO.getId()).ifPresent(userEntity -> {
                userEntity.setEmail(userDTO.getEmail());
                userEntity.setPassword(userDTO.getPassword());
                userEntity.setModifyBy(CommonUtils.getUser().getUsername());
                userRepository.save(userEntity);

                employeeRepository.findById(userEntity.getEmployeeEntity().getId()).ifPresent(employeeEntity -> {
                    employeeEntity.setEmail(userDTO.getEmail());
                    employeeEntity.setModifyBy(CommonUtils.getUser().getUsername());
                    employeeRepository.save(employeeEntity);
                });
            });
            return 200;
        }
        return 500;
    }
}
