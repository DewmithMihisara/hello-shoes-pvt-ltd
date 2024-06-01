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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sale")
public class SaleEntity {
    @Id
    private String saleId;
    private Integer getqty;
    private Double subTotal;
    private String customerName;
    private String cashierName;
    private Integer addedPoints;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @CreationTimestamp
    private Timestamp purchaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private CustomerEntity customerEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity userEntity;

    @ManyToMany
    @JoinTable(
            name = "sale_inventory",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id")
    )
    private List<InventoryEntity> inventories;
}

