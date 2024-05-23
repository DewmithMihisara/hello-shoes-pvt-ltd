package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Gender;
import lk.ijse.helloshoebackend.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity
public class CustomerEntity {
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "registered_date")
    @CreationTimestamp
    private Timestamp registeredDate;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Column(name = "contact", unique = true)
    private String contact;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "recent_purchase_date")
    private java.sql.Date recentPurchaseDate;

    @Embedded
    @Column(name = "address")
    private Address address;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "dob")
    private Date dob;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SaleEntity> sales;
}
