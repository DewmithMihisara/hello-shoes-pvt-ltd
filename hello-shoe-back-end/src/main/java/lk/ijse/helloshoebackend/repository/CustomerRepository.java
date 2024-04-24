package lk.ijse.helloshoebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerRepository, String>{
}
