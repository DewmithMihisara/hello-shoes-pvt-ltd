package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
    @Query("SELECT c.contact FROM CustomerEntity c")
    List<String> findAllByContact();

    CustomerEntity findCustomerByContact(String contact);
}
