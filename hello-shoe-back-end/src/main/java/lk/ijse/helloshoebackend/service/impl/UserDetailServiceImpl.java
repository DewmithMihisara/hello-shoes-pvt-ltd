package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.UserDTO;
import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.UserDetailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;

    public UserDetailServiceImpl(UserRepository userRepository, ModelMapper mapper, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

    @Override
    public UserDTO loginUser(String userName) {
        UserEntity userEntity = userRepository.findById(userName).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with username: " + userName));
        if (userEntity.getRole() == Role.SUPER_ADMIN){
            return new UserDTO(userEntity.getUsername(),"1ai2SKEeXSLhr0XQbI1vKyV35gUwfvCvZ", userEntity.getRole());
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(userEntity.getEmployeeEntity().getEmpId()).orElseThrow(() -> new UsernameNotFoundException("EmployeeEntity not found for userEntity with username: " + userName));
        return new UserDTO(userEntity.getUsername(), employeeEntity.getProfilePic(), userEntity.getRole());
    }
}