package lk.ijse.helloshoebackend.auth;

import lk.ijse.helloshoebackend.enums.Role;

import java.io.Serializable;

/**
 * @author Dewmith Mihisara
 * @date 2024-05-24
 * @since 0.0.1
 */
public record AuthenticationResponse(String jwt, String username, String profilePic, Role role) implements Serializable {
}