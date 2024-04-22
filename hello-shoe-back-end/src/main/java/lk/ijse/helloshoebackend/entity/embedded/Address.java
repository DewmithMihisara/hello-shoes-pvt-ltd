package lk.ijse.helloshoebackend.entity.embedded;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    @Column(length = 100,nullable = false)
    private String line1;

    @Column( length = 100,nullable = false)
    private String line2;

    @Column(length = 100,nullable = false)
    private String line3;

    @Column( length = 100,nullable = false)
    private String line4;

    @Column(length = 100,nullable = false)
    private String line5;

    @Column(length = 100)
    private String line6;
}
