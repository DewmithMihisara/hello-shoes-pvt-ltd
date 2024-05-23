package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.SupplierDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface SupplierService {
    boolean saveSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> getAllSuppliers();

    SupplierDTO getSupplier(String id);

    boolean updateSupplier(SupplierDTO supplierDTO);

    boolean deleteSupplier(String id);

    List<String> getSupplierId();
}
