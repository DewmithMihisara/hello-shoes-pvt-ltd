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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String customerId;
    private String customerName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @CreationTimestamp
    private Timestamp registeredDate;
    private Integer totalPoints;
    @Column(unique = true)
    private String contact;
    private String email;
    private Date recentPurchaseDate;
    private Address address;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Date dob;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SaleEntity> saleEntities;
}
