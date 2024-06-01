package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Gender;
import lk.ijse.helloshoebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    private String empId;
    private String empName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String emergencyContact;
    private String EmergencyInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profilePic;
    private String status;
    @Column(unique = true)
    private String email;
    private String contact;
    private Boolean isActive;
    private String designation;
    private Date dob;
    private Address address;
    @CreationTimestamp
    private Timestamp regDate;
    @ManyToOne
    @JoinColumn(name = "branch_Id", referencedColumnName = "branchId")
    private BranchEntity branchEntity;
}