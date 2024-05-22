package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-05-22
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch")
@Entity
public class BranchEntity implements Serializable {
    @Id
    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "branch_name", unique = true)
    private String branchName;

    @Column(name = "branch_contact")
    private String branchContact;

    @Column(name = "branch_manager")
    private String branchManager;

    @Embedded
    @Column(name = "address")
    private Address address;

    @Column(name = "no_of_employees")
    private Integer noOfEmployees;

    @CreationTimestamp
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<EmployeeEntity> employees;
}
