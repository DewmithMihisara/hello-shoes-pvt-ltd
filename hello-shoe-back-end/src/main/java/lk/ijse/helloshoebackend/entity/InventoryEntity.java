package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.enums.ItemGender;
import lk.ijse.helloshoebackend.enums.ItemStatus;
import lk.ijse.helloshoebackend.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    private String itemCode;
    private String itemDescription;
    private String itemPicture;
    private Integer qtyOnHand;
    private Integer size;
    private Integer discount;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    @Enumerated(EnumType.STRING)
    private ItemGender itemGender;
    private Double buyingPrice;
    private String brand;
    private Double sellingPrice;
    private Double expectedProfit;
    private Double profitMargin;
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
    private String supplierName;
    private Integer itemSoldCount;
    private Integer getStockTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    private SupplierEntity supplierEntity;

    @ManyToMany(mappedBy = "inventories")
    private List<SaleEntity> saleEntities;
}
