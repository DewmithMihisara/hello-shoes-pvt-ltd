package lk.ijse.helloshoebackend.api;

import lk.ijse.helloshoebackend.dto.CustomerDTO;
import lk.ijse.helloshoebackend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        boolean isSave = customerService.saveCustomer(customerDTO);
        return isSave ? ResponseEntity.created(new URI("/customers")).body("CustomerEntity Saved !") : ResponseEntity.badRequest().body("CustomerEntity Save Failed !");
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO){
        boolean isUpdate = customerService.updateCustomer(customerDTO);
        return isUpdate ? ResponseEntity.ok("CustomerEntity Updated !") : ResponseEntity.badRequest().body("CustomerEntity Update Failed !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

    @GetMapping("/contact-list")
    public ResponseEntity<?> getContactList(){
        return ResponseEntity.ok(customerService.getContactList());
    }

    @GetMapping("/get/contact/{id}")
    public ResponseEntity<?> getContact(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomerByContact(id));
    }
}
