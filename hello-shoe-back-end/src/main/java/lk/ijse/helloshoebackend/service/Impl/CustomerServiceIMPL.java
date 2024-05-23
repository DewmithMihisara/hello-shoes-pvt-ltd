package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import lk.ijse.helloshoebackend.entity.CustomerEntity;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.enums.Level;
import lk.ijse.helloshoebackend.repository.CustomerRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.CustomerService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : savindaJ
 * @date : 4/28/2024
 * @since : 0.1.0
 **/
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;

    private final ModelMapper mapper;

    private final UserRepository userRepo;

    public CustomerServiceImpl(CustomerRepository customerRepo, ModelMapper mapper, UserRepository userRepo) {
        this.customerRepo = customerRepo;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }


    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) {
        CustomerEntity map = mapper.map(customerDTO, CustomerEntity.class);
        UserEntity user = userRepo.findById(customerDTO.getUserEmail()).get();
        map.setUser(user);
        map.setCustomerId(IdService.generateID(Id.CUSTOMER_ID));
        map.setLevel(Level.NEW);
        customerRepo.save(map);
        return true;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream().map(customer -> mapper.map(customer,CustomerDTO.class)).toList();
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        CustomerEntity customer = customerRepo.findById(customerDTO.getCustomerId()).get();
        mapper.map(customerDTO,customer);
        customerRepo.save(customer);
        return true;
    }

    @Override
    public CustomerDTO getCustomer(String id) {
        return mapper.map(customerRepo.findById(id).get(),CustomerDTO.class);
    }

    @Override
    public String deleteCustomer(String id) {
        customerRepo.deleteById(id);
        return "Customer Deleted !";
    }

    @Override
    public List<String> getContactList() {
        return customerRepo.findAllByContact();
    }

    @Override
    public CustomerDTO getCustomerByContact(String id) {
        return mapper.map(customerRepo.findCustomerByContact(id),CustomerDTO.class);
    }
}
