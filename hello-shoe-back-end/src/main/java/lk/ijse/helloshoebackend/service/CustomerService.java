package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface CustomerService {
    Integer saveCustomer(CustomerDTO customerDTO);
    Integer updateCustomer(CustomerDTO customerDTO);
    Integer disableCustomer(String id);
    Integer enableCustomer(String id);
    CustomerDTO searchCustomer(String id);
    CustomerDTO searchCustomerByEmail(String email);
    CustomerDTO searchCustomerByDob(String mobile);
    CustomerDTO searchCustomerByContact(String nic);
}
