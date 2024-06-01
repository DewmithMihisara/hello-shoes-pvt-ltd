package lk.ijse.helloshoebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO implements Serializable {
    private String email;
    private String password;
}
