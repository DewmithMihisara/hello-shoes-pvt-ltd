package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.enums.ItemGender;
import lk.ijse.helloshoebackend.enums.ItemStatus;
import lk.ijse.helloshoebackend.enums.ItemType;
import lk.ijse.helloshoebackend.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "inventory")
@Entity
public class InventoryEntity {
    @Id
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_picture")
    private String itemPicture;

    @Column(name = "qty_on_hand")
    private Integer qtyOnHand;

    @Column(name = "size")
    private Integer size;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "item_gender")
    @Enumerated(EnumType.STRING)
    private ItemGender itemGender;

    @Column(name = "buying_price")
    private Double buyingPrice;

    @Column(name = "brand")
    private String brand;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "expected_profit")
    private Double expectedProfit;

    @Column(name = "profit_margin")
    private Double profitMargin;

    @Column(name = "item_status")
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "item_sold_count")
    private Integer itemSoldCount;

    @Column(name = "get_stock_total")
    private Integer getStockTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    private SuppliersEntity supplier;

    @ManyToMany(mappedBy = "inventories")
    private List<SaleEntity> sales;
}
