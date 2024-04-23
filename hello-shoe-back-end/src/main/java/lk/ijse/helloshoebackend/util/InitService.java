package lk.ijse.helloshoebackend.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.helloshoebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lk.ijse.helloshoebackend.entity.UserEntity;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@Service
public class InitService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostConstruct
//    public void init() {
//        addDefuelEmployee();
//        if (userRepository.count() == 0) {
//            userRepository.save(
//                    UserEntity.builder()
//                            .id("usr-001")
//                            .password("$2a$12$LO2YSeo9irJxjVGxyPX50uycBslOSfWdMDI8/2Aa0Ff5dbPtMaA5e") //Admin@123
//                            .email("admin@gmail.com")
//                            .role(Constants.ADMIN_USER)
//                            .createBy("System")
//                            .modifyBy("System")
//                            .isActive(Constants.ACTIVE)
//                            .build()
//            );
//        }
//    }
//
//    private void addDefuelEmployee() {
//
//    }
}
