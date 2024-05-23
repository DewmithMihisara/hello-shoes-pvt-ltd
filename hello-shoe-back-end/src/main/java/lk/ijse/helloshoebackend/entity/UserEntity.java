package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-20
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "user")
    private List<CustomerEntity> customers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SaleEntity> sales;

    public UserEntity(String email, String password, Role role) {
        this.username = email;
        this.password = password;
        this.role = role;
    }
}
