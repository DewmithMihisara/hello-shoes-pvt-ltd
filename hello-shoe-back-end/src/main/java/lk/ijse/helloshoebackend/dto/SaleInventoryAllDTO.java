package lk.ijse.helloshoebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInventoryAllDTO {
    private String saleId;
    private String inventoryId;
    private int qty;
}
