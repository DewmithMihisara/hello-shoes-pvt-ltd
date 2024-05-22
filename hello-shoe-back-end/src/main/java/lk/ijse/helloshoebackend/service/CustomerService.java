package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-27
 * @since 0.0.1
 */
@Service
public interface CustomerService {
    Boolean existsByEmailAndContact(String email, String contact);
    Integer saveCustomer(CustomerDTO customerDTO);
    Integer updateCustomer(CustomerDTO customerDTO);
    Integer disableCustomer(String id);
    Integer enableCustomer(String id);
    CustomerDTO searchCustomer(String id);
    List<CustomerDTO> getAllCustomers();
}
