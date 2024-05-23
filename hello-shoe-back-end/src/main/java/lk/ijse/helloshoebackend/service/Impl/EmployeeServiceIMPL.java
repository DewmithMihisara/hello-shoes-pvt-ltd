package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.EmployeeDTO;
import lk.ijse.helloshoebackend.entity.BranchEntity;
import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.repository.BranchRepository;
import lk.ijse.helloshoebackend.repository.EmployeeRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.BranchService;
import lk.ijse.helloshoebackend.service.EmployeeService;
import lk.ijse.helloshoebackend.service.UploadService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UploadService uploadService;

    private final ModelMapper mapper;

    private final EmployeeRepository employeeRepo;

    private final BranchService branchService;

    private final UserRepository userRepo;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final BranchRepository branchRepo;

    public EmployeeServiceImpl(UploadService uploadService, ModelMapper mapper, EmployeeRepository employeeRepo, BranchService branchService, UserRepository userRepo, PasswordEncoder bCryptPasswordEncoder, BranchRepository branchRepo) {
        this.uploadService = uploadService;
        this.mapper = mapper;
        this.employeeRepo = employeeRepo;
        this.branchService = branchService;
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.branchRepo = branchRepo;
    }

    @Transactional
    @Override
    public boolean saveEmployee(EmployeeDTO employee, MultipartFile file) throws IOException {
        String imageId = uploadService.uploadFile(file);
        BranchEntity branch = branchService.getBranchById(employee.getBranchId());
        if (employee.getRole() == Role.ADMIN) {
            branch.setBranchManager(employee.getEmpName());
        }
        branch.setNoOfEmployees(branch.getNoOfEmployees() == null ? 1 : branch.getNoOfEmployees() + 1);
        branchRepo.save(branch);
        if (imageId == null) return false;
        EmployeeEntity map = mapper.map(employee, EmployeeEntity.class);
        map.setIsActive(true);
        map.setEmpId(IdService.generateID(Id.EMPLOYEE_ID));
        map.setBranch(branch);
        map.setProfilePic(imageId);
        employeeRepo.save(map);

        UserEntity user = new UserEntity();
        String password = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("password = " + password);
        user.setUsername(employee.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(employee.getRole());
        user.setEmployee(map);
        userRepo.save(user);
        return true;
    }

    @Override
    public List<EmployeeDTO> getAllAdmins() {
        return employeeRepo.findAllByRole(Role.ADMIN).stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public List<EmployeeDTO> getAllCashiers() {
        return employeeRepo.findAllByRole(Role.USER).stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public EmployeeDTO getEmployee(String empId) {
        return mapper.map(employeeRepo.findById(empId).orElseThrow(), EmployeeDTO.class);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO,MultipartFile file) throws IOException {
        EmployeeEntity employee = employeeRepo.findById(employeeDTO.getEmpId()).orElseThrow();
        BranchEntity branch = branchService.getBranchById(employeeDTO.getBranchId());
        employee.setBranch(branch);
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setAddress(employeeDTO.getAddress());
        employee.setContact(employeeDTO.getContact());
        employee.setRole(employeeDTO.getRole());
        employee.setEmergencyInfo(employeeDTO.getEmergencyInfo());
        employee.setEmergencyContact(employeeDTO.getEmergencyContact());
        employee.setDob(employeeDTO.getDob());
        employee.setGender(employeeDTO.getGender());
        employee.setAddress(employeeDTO.getAddress());
        if (!file.getOriginalFilename().equals("notUpdate")){
            String image = uploadService.uploadFile(file);
            employee.setProfilePic(image);
        }
        employeeRepo.save(employee);
        return true;
    }
}
