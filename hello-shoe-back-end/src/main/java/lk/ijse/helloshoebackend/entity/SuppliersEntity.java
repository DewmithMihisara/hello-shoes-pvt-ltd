package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.entity.embedded.Contact;
import lk.ijse.helloshoebackend.enums.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class SuppliersEntity {
    @Id
    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_category")
    @Enumerated(EnumType.STRING)
    private SupplierCategory supplierCategory;

    @Embedded
    @Column(name = "contact")
    private Contact contact;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;

    @Embedded
    @Column(name = "address")
    private Address address;

    @Column(name = "origin_country")
    private String originCountry;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<InventoryEntity> inventories;
}
