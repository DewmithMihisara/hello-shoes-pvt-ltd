package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.EmployeeEntity;
import lk.ijse.helloshoebackend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
        List<EmployeeEntity> findAllByRole(Role role);
}
