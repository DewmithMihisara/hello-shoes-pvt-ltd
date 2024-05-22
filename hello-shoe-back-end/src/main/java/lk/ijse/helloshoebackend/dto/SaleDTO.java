package lk.ijse.helloshoebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.helloshoebackend.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO implements Serializable {
    private String saleId;
    private Double subTotal;
    private String customerContact;
    private String customerName;
    private Integer addedPoints;
    private String cashierName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PaymentMethod paymentMethod;
    private Boolean isDemo;
    private List<InventoryDTO> inventories;
    private Integer getqty;
}
