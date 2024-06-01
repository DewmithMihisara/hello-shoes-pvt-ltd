package lk.ijse.helloshoebackend.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-06-01
 * @since 0.0.1
 */
@Service
public class InitService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepo;

    private final DbService dbService;

    public InitService(PasswordEncoder passwordEncoder, UserRepository userRepo, DbService dbService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.dbService = dbService;
    }

//    @PostConstruct
//    public void dbAlignment() {
//        dbService.addQtyColumnToSaleInventory();
//    }

    @PostConstruct
    public void init() {
        if (userRepo.count() == 0) {
            userRepo.save(UserEntity
                    .builder()
                    .role(Role.SUPER_ADMIN)
                    .username("mihisara@gmail.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .build());
            dbService.addQtyColumnToSaleInventory();
        }
    }
}
