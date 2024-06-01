package lk.ijse.helloshoebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.helloshoebackend.entity.embedded.Address;
import lk.ijse.helloshoebackend.entity.embedded.Contact;
import lk.ijse.helloshoebackend.enums.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private String supplierCode;
    private String supplierName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SupplierCategory supplierCategory;
    private Contact contact;
    private String email;
    private Boolean isActive;
    private Address address;
    private String originCountry;
}