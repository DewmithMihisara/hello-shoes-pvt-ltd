package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.CustomerDTO;

import java.util.List;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
public interface CustomerService {
    boolean saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    boolean updateCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(String id);

    boolean deleteCustomer(String id);

    List<String> getContactList();

    CustomerDTO getCustomerByContact(String id);
}
