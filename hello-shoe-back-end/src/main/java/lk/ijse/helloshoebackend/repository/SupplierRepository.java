package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {
    List<SupplierEntity> findAllByIsActive(boolean isActive);
}
