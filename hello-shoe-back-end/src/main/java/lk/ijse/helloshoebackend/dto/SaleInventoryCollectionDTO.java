package lk.ijse.helloshoebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInventoryCollectionDTO {
    private SaleDTO saleDTO;
    private SaleInventoryDTO saleInventoryDTO;
    private List<InventoryDTO> inventoryDTOList;
}
