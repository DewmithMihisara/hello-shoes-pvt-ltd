package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    Boolean existsByEmailAndContact(String email, String contact);
}
