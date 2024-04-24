package lk.ijse.helloshoebackend.service.Impl;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import lk.ijse.helloshoebackend.entity.CustomerEntity;
import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.repository.CustomerRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.CustomerService;
import lk.ijse.helloshoebackend.util.CommonUtils;
import lk.ijse.helloshoebackend.util.Constants;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class CustomerServiceIMPL implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceIMPL(CustomerRepository customerRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Integer saveCustomer(CustomerDTO customerDTO) {
        if (! customerRepository.existsByEmailAndContact(customerDTO.getEmail(), customerDTO.getContact())) {
            CustomerEntity map = modelMapper.map(customerDTO, CustomerEntity.class);
            Optional<UserEntity> byId = userRepository.findByEmail(CommonUtils.getUser().getUsername());
            map.setUserEntity(byId.get());
            map.setJoinedDate(CommonUtils.getCurrentDateTime());
            map.setId(IdService.generateID(Constants.CUSTOMER_ID));
            map.setIsActive(Constants.ACTIVE);
            map.setLevel(Constants.NEW);
            map.setTtlPoints(0);
            map.setCreateBy(CommonUtils.getUser().getUsername());
            return saveOrUpdate(customerDTO, map);

        }
        return 500;
    }

    @Override
    public Integer updateCustomer(CustomerDTO customerDTO) {
        if (! customerRepository.existsByEmailAndContact(customerDTO.getEmail(), customerDTO.getContact())) {
            CustomerEntity map = modelMapper.map(customerDTO, CustomerEntity.class);
            return saveOrUpdate(customerDTO, map);

        }
        return 500;
    }

    private Integer saveOrUpdate(CustomerDTO customerDTO, CustomerEntity map) {
        map.setModifyBy(CommonUtils.getUser().getUsername());
        map.setDob(CommonUtils.convertStringToDate(customerDTO.getDob()));

        map.setAddress(Address
                .builder()
                .line1(customerDTO.getLine1())
                .line2(customerDTO.getLine2())
                .line3(customerDTO.getLine3())
                .line4(customerDTO.getLine4())
                .line5(customerDTO.getLine5())
                .build());

        if (customerDTO.getGender().equals("MAN")) {
            map.setGender(Constants.MAN);
        }else {
            map.setGender(Constants.WOMAN);
        }

        customerRepository.save(map);

        return 200;
    }

    @Override
    public Integer disableCustomer(String id) {
        return activation(id, Constants.INACTIVE);
    }

    @Override
    public Integer enableCustomer(String id) {
        return activation(id, Constants.ACTIVE);
    }

    public Integer activation(String id , Constants constants){
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        if (customerEntity != null) {
            customerEntity.setIsActive(constants);
            customerEntity.setModifyBy(CommonUtils.getUser().getUsername());
            customerRepository.save(customerEntity);
            return 200;
        }
        return 500;
    }

    @Override
    public CustomerDTO searchCustomer(String id) {
        return null;
    }

    @Override
    public CustomerDTO searchCustomerByEmail(String email) {
        return null;
    }

    @Override
    public CustomerDTO searchCustomerByDob(String mobile) {
        return null;
    }

    @Override
    public CustomerDTO searchCustomerByContact(String nic) {
        return null;
    }
}
