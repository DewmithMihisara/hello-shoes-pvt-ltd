package lk.ijse.helloshoebackend.api;

import lk.ijse.helloshoebackend.dto.SaleDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryCollectionDTO;
import lk.ijse.helloshoebackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-22
 * @since 0.0.1
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> saveSale(@RequestBody SaleDTO saleDTO){
        boolean isPlaced = saleService.saveSale(saleDTO);
        return isPlaced ? ResponseEntity.ok("SaleEntity Placed") : ResponseEntity.badRequest().body("SaleEntity Not Placed");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSale(@RequestBody SaleInventoryCollectionDTO saleInventoryCollectionDTO){
        boolean isPlaced = saleService.updateSale(saleInventoryCollectionDTO);
        return isPlaced ? ResponseEntity.ok("SaleEntity Updated") : ResponseEntity.badRequest().body("SaleEntity Not Updated");
    }

    @GetMapping
    public ResponseEntity<?> getAllSales(){
        return ResponseEntity.ok(saleService.getAllSales());
    }
}
