package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.SaleDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryCollectionDTO;

import java.util.List;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
public interface SaleService {
    boolean saveSale(SaleDTO saleDTO);
    boolean updateSale(SaleInventoryCollectionDTO saleInventoryCollectionDTO);
    List<SaleDTO> getAllSales();
}
