package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String id;

    @Column(name = "itm_qty")
    private Integer itmQty;

    @Column(name = "ttl_price")
    private Double ttlPrice;

    @Column(name = "cus_name")
    private String cusName;

    @Column(name = "cashier")
    private String cashier;

    @Column(name = "added_points")
    private Integer addedPoints;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "perchased_date")
    private String perchasedDate;

    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "cus_id")
    private CustomerEntity customerEntity;

}