package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String>{
    CustomerEntity findCustomerEntityByEmail(String email);
    CustomerEntity findCustomerEntityByContact(String mobile);
    List<CustomerEntity> findCustomerEntitiesByDob(Date dob);
    boolean existsByEmailAndContact(String email, String contact);
}
