package lk.ijse.helloshoebackend.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String mobile;
    private String land;
}
