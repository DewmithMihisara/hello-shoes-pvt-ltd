package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface UserDetailService {
    UserDTO loginUser(String userName);
}
