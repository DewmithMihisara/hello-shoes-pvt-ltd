package lk.ijse.helloshoebackend.controller;

import lk.ijse.helloshoebackend.dto.ResponseDTO;
import lk.ijse.helloshoebackend.dto.SupplierDTO;
import lk.ijse.helloshoebackend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/v1/supplier")
@CrossOrigin(origins = "*")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseDTO saveOrUpdate(@RequestBody SupplierDTO supplierDTO) {
        try {
            if (supplierDTO.getId() == null) {
                return new ResponseDTO("success", supplierService.saveSupplier(supplierDTO));
            } else {
                return new ResponseDTO("success", supplierService.updateSupplier(supplierDTO));
            }
        }catch (Exception e){
            return new ResponseDTO(e.getMessage(), 500);
        }
    }

    @PutMapping("/dis/{id}")
    public ResponseDTO disable(@PathVariable String id) {
        try {
            return new ResponseDTO("success", supplierService.disable(id));
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), 500);
        }
    }

    @PutMapping("/enb/{id}")
    public ResponseDTO enable(@PathVariable String id) {
        try {
            return new ResponseDTO("success", supplierService.enable(id));
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseDTO search(@PathVariable String id) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("supplier", supplierService.searchSupplier(id));
            return new ResponseDTO("success", 200, map);
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), 500);
        }
    }

    @GetMapping
    public ResponseDTO getAll() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("suppliers", supplierService.getAllSuppliers());
            return new ResponseDTO("success", 200, map);
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), 500);
        }
    }
}