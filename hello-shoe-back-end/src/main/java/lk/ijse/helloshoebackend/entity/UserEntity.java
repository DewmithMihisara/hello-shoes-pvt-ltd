package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.enums.Role;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeEntity employeeEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<CustomerEntity> customerEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<SaleEntity> saleEntities;

    public UserEntity(String email, String password, Role role) {
        this.username = email;
        this.password = password;
        this.role = role;
    }
}
