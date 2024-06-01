package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
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
@Table(name = "branch")
public class BranchEntity {
    @Id
    private String branchId;
    @Column(unique = true)
    private String branchName;
    private String branchContact;
    private String branchManager;
    private Address address;
    private Integer noOfEmployees;
    @CreationTimestamp
    private Timestamp createdDate;

    @OneToMany(mappedBy = "branchEntity", cascade = CascadeType.ALL)
    private List<EmployeeEntity> employeeEntities;
}