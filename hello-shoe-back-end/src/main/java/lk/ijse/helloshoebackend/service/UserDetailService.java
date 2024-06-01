package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.UserDTO;

public interface UserDetailService {
    UserDTO loginUser(String userName);
}
