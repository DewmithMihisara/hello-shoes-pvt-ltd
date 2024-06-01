package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,String> {
}
