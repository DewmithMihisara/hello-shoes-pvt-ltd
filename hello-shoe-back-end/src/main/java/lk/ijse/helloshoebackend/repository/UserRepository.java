package lk.ijse.helloshoebackend.repository;

import lk.ijse.helloshoebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByName(String username);
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);
}
