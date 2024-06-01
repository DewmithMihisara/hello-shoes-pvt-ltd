package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.EmailDTO;
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
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UploadService uploadService;

    private final ModelMapper mapper;

    private final EmployeeRepository employeeRepository;

    private final BranchService branchService;

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final BranchRepository branchRepository;

    private final EmailService emailService;

    public EmployeeServiceImpl(UploadService uploadService, ModelMapper mapper, EmployeeRepository employeeRepository, BranchService branchService, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, BranchRepository branchRepository, EmailService emailService) {
        this.uploadService = uploadService;
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.branchService = branchService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.branchRepository = branchRepository;
        this.emailService = emailService;
    }

    //f5a24919

    //fefe3cc8


    @Transactional
    @Override
    public boolean saveEmployee(EmployeeDTO employee, MultipartFile file) throws IOException {
        String imageId = uploadService.uploadFile(file);
        BranchEntity branchEntity = branchService.getBranchById(employee.getBranchId());
        if (employee.getRole() == Role.ADMIN) {
            branchEntity.setBranchManager(employee.getEmpName());
        }
        branchEntity.setNoOfEmployees(branchEntity.getNoOfEmployees() == null ? 1 : branchEntity.getNoOfEmployees() + 1);
        branchRepository.save(branchEntity);
        if (imageId == null) return false;
        EmployeeEntity map = mapper.map(employee, EmployeeEntity.class);
        map.setIsActive(true);
        map.setEmpId(IdService.generateID(Id.EMPLOYEE_ID));
        map.setBranchEntity(branchEntity);
        map.setProfilePic(imageId);
        employeeRepository.save(map);

        UserEntity userEntity = new UserEntity();
        String password = UUID.randomUUID().toString().substring(0, 8);

        System.out.println("password = " + password);
        userEntity.setUsername(employee.getEmail());

        emailService.sendSimpleMail(EmailDTO
                .builder()
                .msgBody("Your Password is : "+password)
                .subject("Your password")
                .recipient("theekshanaroxx0525@gmail.com")
                .build());

        userEntity.setPassword(bCryptPasswordEncoder.encode(password));
        userEntity.setRole(employee.getRole());
        userEntity.setEmployeeEntity(map);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public List<EmployeeDTO> getAllAdmins() {
        return employeeRepository.findAllByRole(Role.ADMIN).stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public List<EmployeeDTO> getAllCashiers() {
        return employeeRepository.findAllByRole(Role.USER).stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public EmployeeDTO getEmployee(String empId) {
        return mapper.map(employeeRepository.findById(empId).orElseThrow(), EmployeeDTO.class);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO,MultipartFile file) throws IOException {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeDTO.getEmpId()).orElseThrow();
        BranchEntity branchEntity = branchService.getBranchById(employeeDTO.getBranchId());
        employeeEntity.setBranchEntity(branchEntity);
        employeeEntity.setEmpName(employeeDTO.getEmpName());
        employeeEntity.setAddress(employeeDTO.getAddress());
        employeeEntity.setContact(employeeDTO.getContact());
        employeeEntity.setRole(employeeDTO.getRole());
        employeeEntity.setEmergencyInfo(employeeDTO.getEmergencyInfo());
        employeeEntity.setEmergencyContact(employeeDTO.getEmergencyContact());
        employeeEntity.setDob(employeeDTO.getDob());
        employeeEntity.setGender(employeeDTO.getGender());
        employeeEntity.setStatus(employeeDTO.getStatus());
        employeeEntity.setAddress(employeeDTO.getAddress());
        if (!file.getOriginalFilename().equals("notUpdate")){
            String image = uploadService.uploadFile(file);
            employeeEntity.setProfilePic(image);
        }
        employeeRepository.save(employeeEntity);
        return true;
    }
}
