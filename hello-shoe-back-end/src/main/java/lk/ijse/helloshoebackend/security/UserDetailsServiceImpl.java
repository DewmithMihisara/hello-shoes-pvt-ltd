package lk.ijse.helloshoebackend.security;

import lk.ijse.helloshoebackend.entity.UserEntity;
import lk.ijse.helloshoebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(name).orElse(null);

        if(user == null) {
            throw new UsernameNotFoundException("UserEntity not found with the given email");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
