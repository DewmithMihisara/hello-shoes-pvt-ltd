package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.SaleInventoryDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-05-30
 * @since 0.0.1
 */
@Service
public interface SaleInventoryService {
    SaleInventoryDTO getSaleInventoryDataBySaleId(String saleId);
}
