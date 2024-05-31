package lk.ijse.helloshoebackend.api;

import lk.ijse.helloshoebackend.dto.SaleInventoryDTO;
import lk.ijse.helloshoebackend.service.impl.SaleInventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale-inventory")
public class SaleInventoryController {

    @Autowired
    private SaleInventoryServiceImpl saleInventoryService;

    @GetMapping("/{saleId}")
    public SaleInventoryDTO getSaleInventoryDataBySaleId(@PathVariable String saleId) {
        return saleInventoryService.getSaleInventoryDataBySaleId(saleId);
    }
}

