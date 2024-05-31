package lk.ijse.helloshoebackend.api;

import lk.ijse.helloshoebackend.auth.AuthenticationResponse;
import lk.ijse.helloshoebackend.dto.LoginDTO;
import lk.ijse.helloshoebackend.dto.UserDTO;
import lk.ijse.helloshoebackend.enums.Role;
import lk.ijse.helloshoebackend.security.jwt.JwtUtil;
import lk.ijse.helloshoebackend.service.UserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtils;

    private final UserDetailsService userDetailsService;

    private final UserDetailService userDetailService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtils, UserDetailsService userDetailsService, UserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UserDTO userDto = null;
        String profilePic = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginDTO.getEmail());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            userDto = userDetailService.loginUser(userDetails.getUsername());
            if (userDto.getRole().equals(Role.SUPER_ADMIN)) {
                profilePic = "11znDDHfMXG2uVpAw9H3JU2sMchS5r8_u";
            } else {
                profilePic = userDto.getProfilePic();
            }
            return ResponseEntity.ok(new AuthenticationResponse(jwt, userDto.getUserName(), profilePic, userDto.getRole()));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
    @PostMapping("/verification")
    public ResponseEntity<?> verifyAdminPassword(@RequestBody LoginDTO userDataDTO) {
        UserDTO userdto = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDataDTO.getEmail());
            boolean passwordMatches = passwordEncoder.matches(userDataDTO.getPassword(), userDetails.getPassword());
            userdto = userDetailService.loginUser(userDetails.getUsername());
            if (passwordMatches && (userdto.getRole().equals(Role.SUPER_ADMIN)|| userdto.getRole().equals(Role.ADMIN))){
                return ResponseEntity.ok().body("Password verified successfully");
            } else {
                return ResponseEntity.status(401).body("Incorrect password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying password");
        }
    }
}