package lk.ijse.helloshoebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInventoryDTO {
    private String saleId;
    private List<InventoryQtyDTO> inventoryDetails;
}
