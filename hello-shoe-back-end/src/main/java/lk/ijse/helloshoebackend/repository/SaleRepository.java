package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dewmith Mihisara
 * @date 2024-05-22
 * @since 0.0.1
 */
@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, String> {
}
