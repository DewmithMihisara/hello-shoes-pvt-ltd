package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.UserDTO;
import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.UserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class UserDetailServiceImpl implements UserDetailService, UserDetailsService {

    private final UserRepository userRepo;

    private final ModelMapper mapper;

    private final EmployeeRepository employeeRepo;

    public UserDetailServiceImpl(UserRepository userRepo, ModelMapper mapper, EmployeeRepository employeeRepo) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findById(username).get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public UserDTO loginUser(String userName) {
        UserEntity user = userRepo.findById(userName).get();
        if (user.getRole() == Role.SUPER_ADMIN){
            return new UserDTO(user.getUsername(),"1ai2SKEeXSLhr0XQbI1vKyV35gUwfvCvZ",user.getRole());
        }
        EmployeeEntity employee = employeeRepo.findById(user.getEmployee().getEmpId()).get();
        return new UserDTO(user.getUsername(),employee.getProfilePic(),user.getRole());
    }
}
