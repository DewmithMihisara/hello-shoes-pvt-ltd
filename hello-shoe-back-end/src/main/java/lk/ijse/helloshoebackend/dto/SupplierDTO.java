package lk.ijse.helloshoebackend.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupplierDTO implements Serializable {
    private String id;
    private String name;
    private String category;
    private String email;
    private String mobileContact;
    private String landContact;
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;
    private String line6;
}
