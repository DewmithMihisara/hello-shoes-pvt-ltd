package lk.ijse.helloshoebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Gender;
import lk.ijse.helloshoebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String empId;
    private String empName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private String emergencyContact;
    private String EmergencyInfo;
    private String status;
    private String email;
    private String contact;
    private String designation;
    private Date dob;
    private Address address;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;
    private String profilePic;
    private Boolean isActive;
    private String branchId;
    private String branchName;
}