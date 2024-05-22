package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.SuppliersEntity;
import lk.ijse.helloshoebackend.entity.embedded.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Repository
public interface SupplierRepository extends JpaRepository<SuppliersEntity, String> {
    Boolean existsByContactAndEmail(Contact contact, String email);
}
