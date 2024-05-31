package lk.ijse.helloshoebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleInventoryCollectionDTO {
    private SaleDTO saleDTO;
    private SaleInventoryDTO saleInventoryDTO;
    private List<InventoryDTO> inventoryDTOList;
}
