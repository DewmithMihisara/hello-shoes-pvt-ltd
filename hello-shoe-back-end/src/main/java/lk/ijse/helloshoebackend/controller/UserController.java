package lk.ijse.helloshoebackend.controller;

import lk.ijse.helloshoebackend.dto.ResponseDTO;
import lk.ijse.helloshoebackend.dto.UserDTO;
import lk.ijse.helloshoebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return new ResponseDTO("success", userService.updateUsr(userDTO));
    }
}
