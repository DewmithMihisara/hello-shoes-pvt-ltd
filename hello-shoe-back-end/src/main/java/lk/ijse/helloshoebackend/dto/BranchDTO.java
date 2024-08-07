package lk.ijse.helloshoebackend.dto;

import lk.ijse.helloshoebackend.entity.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private String branchId;
    private String branchName;
    private String branchContact;
    private Address address;
    private Integer noOfEmployees;
    private String branchManager;
    private Timestamp createdDate;
}