package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.SupplierDTO;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
public interface SupplierService {
    Integer saveSupplier(SupplierDTO supplierDTO);
    Integer updateSupplier(SupplierDTO supplierDTO);
    Integer disable(String id);
    Integer enable(String id);
    SupplierDTO searchSupplier(String id);
    List<SupplierDTO> getAllSuppliers();
}
