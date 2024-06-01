package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.UserDTO;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

public interface UserDetailService {
    UserDTO loginUser(String userName);
}
