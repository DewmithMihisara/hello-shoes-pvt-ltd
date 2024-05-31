package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.SaleDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryCollectionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface SaleService {
    boolean saveSale(SaleDTO saleDTO);
    boolean updateSale(SaleInventoryCollectionDTO saleInventoryCollectionDTO);
    List<SaleDTO> getAllSales();
}
