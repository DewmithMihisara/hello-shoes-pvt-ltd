package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface CustomerService {
    boolean saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    boolean updateCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(String id);

    String deleteCustomer(String id);

    List<String> getContactList();

    CustomerDTO getCustomerByContact(String id);
}
