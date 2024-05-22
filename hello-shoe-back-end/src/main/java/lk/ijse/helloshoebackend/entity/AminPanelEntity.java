package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin_panel")
@Entity
public class AminPanelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_sales", columnDefinition = "DECIMAL(20,2)")
    private Double totalSales;

    @Column(name = "total_profit", columnDefinition = "DECIMAL(20,2)")
    private Double totalProfit;

    @Column(name = "most_sales_item")
    private String mostSalesItem;

    @Column(name = "most_sales_item_qty")
    private Integer mostSalesItemQty;

    @Column(name = "most_sales_item_pic")
    private String mostSalesItemPic;
}
