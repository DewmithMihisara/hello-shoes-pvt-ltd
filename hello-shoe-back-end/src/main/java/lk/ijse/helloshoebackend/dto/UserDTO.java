package lk.ijse.helloshoebackend.dto;

import lk.ijse.helloshoebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private String userName;
    private String profilePic;
    private Role role;
}
