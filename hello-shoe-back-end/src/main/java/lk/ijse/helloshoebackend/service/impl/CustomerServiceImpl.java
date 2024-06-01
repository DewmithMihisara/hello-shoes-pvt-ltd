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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final IdService idService;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper, UserRepository userRepository, IdService idService) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.idService = idService;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) {
        logger.info("Saving customer with email: {}", customerDTO.getUserEmail());

        CustomerEntity map = mapper.map(customerDTO, CustomerEntity.class);
        UserEntity userEntity = userRepository.findById(customerDTO.getUserEmail()).orElse(null);
        if (userEntity == null) {
            logger.warn("UserEntity with email {} not found", customerDTO.getUserEmail());
            return false;
        }
        map.setUserEntity(userEntity);
        map.setCustomerId(IdService.generateID(Id.CUSTOMER_ID));
        map.setLevel(Level.NEW);
        customerRepository.save(map);
        logger.info("CustomerEntity saved with ID: {}", map.getCustomerId());

        return true;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Fetching all customers");
        List<CustomerDTO> customers = customerRepository.findAll().stream()
                .map(customer -> mapper.map(customer, CustomerDTO.class))
                .toList();
        logger.info("Total customers fetched: {}", customers.size());

        return customers;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        logger.info("Updating customerEntity with ID: {}", customerDTO.getCustomerId());

        CustomerEntity customerEntity = customerRepository.findById(customerDTO.getCustomerId()).orElse(null);
        if (customerEntity == null) {
            logger.warn("CustomerEntity with ID {} not found", customerDTO.getCustomerId());
            return false;
        }
        customerEntity.setCustomerName(customerDTO.getCustomerName());
        customerEntity.setGender(customerDTO.getGender());
        customerEntity.setContact(customerDTO.getContact());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setAddress(customerDTO.getAddress());
        customerEntity.setDob(customerDTO.getDob());
        customerRepository.save(customerEntity);
        logger.info("CustomerEntity updated with ID: {}", customerEntity.getCustomerId());

        return true;
    }

    @Override
    public CustomerDTO getCustomer(String id) {
        logger.info("Fetching customerEntity with ID: {}", id);

        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        if (customerEntity == null) {
            logger.warn("CustomerEntity with ID {} not found", id);
            return null;
        }
        CustomerDTO customerDTO = mapper.map(customerEntity, CustomerDTO.class);
        logger.info("CustomerEntity fetched with ID: {}", id);

        return customerDTO;
    }

    @Override
    public boolean deleteCustomer(String id) {
        logger.info("Deleting customer with ID: {}", id);

         customerRepository.deleteById(id);
        logger.info("CustomerEntity deleted with ID: {}", id);

        return true;
    }

    @Override
    public List<String> getContactList() {
        logger.info("Fetching all customer contacts");
        List<String> contacts = customerRepository.findAllByContact();
        logger.info("Total contacts fetched: {}", contacts.size());

        return contacts;
    }

    @Override
    public CustomerDTO getCustomerByContact(String contact) {
        logger.info("Fetching customerEntity with contact: {}", contact);

        CustomerEntity customerEntity = customerRepository.findCustomerByContact(contact);
        if (customerEntity == null) {
            logger.warn("CustomerEntity with contact {} not found", contact);
            return null;
        }
        CustomerDTO customerDTO = mapper.map(customerEntity, CustomerDTO.class);
        logger.info("CustomerEntity fetched with contact: {}", contact);

        return customerDTO;
    }
}