package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sale")
public class SaleEntity {
    @Id
    @Column(name = "sale_id")
    private String saleId;

    //    private String itemDescription;

    @Column(name = "getqty")
    private Integer getqty;

//    private Integer size;

//    private Double unitPrice;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "cashier_name")
    private String cashierName;

    @Column(name = "added_points")
    private Integer addedPoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    @Column(name = "purchase_date")
    private Timestamp purchaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "sale_inventory",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id")
    )
    private List<InventoryEntity> inventories;
}
