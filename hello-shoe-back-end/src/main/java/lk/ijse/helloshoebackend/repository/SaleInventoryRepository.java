package lk.ijse.helloshoebackend.repository;


import lk.ijse.helloshoebackend.entity.SaleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Repository
public interface SaleInventoryRepository extends CrudRepository<SaleEntity, String> {

    @Query(value = "SELECT inventory_id,qty FROM sale_inventory WHERE sale_id = :saleId", nativeQuery = true)
    List<Object[]> findInventoryQtyBySaleId(@Param("saleId") String saleId);

    @Modifying
    @Query(value = "UPDATE sale_inventory SET qty = :qty WHERE sale_id = :saleId AND inventory_id = :itemcode", nativeQuery = true)
    void updateSaleInventoryQty(@Param("saleId") String saleId, @Param("itemcode") String itemcode, @Param("qty") int qty);
}