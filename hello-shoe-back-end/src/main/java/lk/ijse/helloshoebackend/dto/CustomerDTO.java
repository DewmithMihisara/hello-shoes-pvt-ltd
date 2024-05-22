package lk.ijse.helloshoebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.enums.Gender;
import lk.ijse.helloshoebackend.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-27
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDTO implements Serializable {
    private String userEmail;
    private String customerId;
    private String customerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private Integer totalPoints;
    private String contact;
    private String email;
    private java.sql.Date recentPurchaseDate;
    private Address address;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Level level;
    private Date dob;
    private Timestamp registeredDate;
}
