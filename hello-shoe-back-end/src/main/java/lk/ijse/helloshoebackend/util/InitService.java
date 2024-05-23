package lk.ijse.helloshoebackend.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lk.ijse.helloshoebackend.entity.UserEntity;

import java.time.LocalDate;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@Service
public class InitService {

    private final UserRepository userRepository;

    public InitService(UserRepository userRepository) {
        this.userRepository = userRepository;
        initUser();
    }

    private void initUser() {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity
                    .builder()
                    .username("admin@gmai.com")
                    .password("$2a$12$GjVlMV9LqXNo5.Hq.SuBBuMj7Td7zDEz4x8lTqJRScviDtU/7bRR2") // Admin@123
                    .role(Role.SUPER_ADMIN)
                    .build());
        }
    }
}
