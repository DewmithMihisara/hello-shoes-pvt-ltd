package lk.ijse.helloshoebackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Gender;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee")
@Entity
public class EmployeeEntity {
    @Id
    private String empId; // 299e671f  // 8aeffaa6

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_info")
    private String EmergencyInfo;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "status")
    private String status;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "designation")
    private String designation;

    @Column(name = "dob")
    private Date dob;

    @Embedded
    @Column(name = "address")
    private Address address;

    @CreationTimestamp
    @Column(name = "reg_date")
    private Timestamp regDate;

    @ManyToOne
    @JoinColumn(name = "branch_Id", referencedColumnName = "branch_id")
    private BranchEntity branch;
}
