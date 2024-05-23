package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.SupplierDTO;
import lk.ijse.helloshoebackend.entity.SuppliersEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.repository.SupplierRepository;
import lk.ijse.helloshoebackend.service.SupplierService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;

    private final ModelMapper mapper;

    public SupplierServiceImpl(SupplierRepository supplierRepo, ModelMapper mapper) {
        this.supplierRepo = supplierRepo;
        this.mapper = mapper;
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setSupplierCode(IdService.generateID(Id.SUPPLIER_ID));
        supplierDTO.setIsActive(true);
        SuppliersEntity save = supplierRepo.save(mapper.map(supplierDTO, SuppliersEntity.class));
        return save != null;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepo.findAllByIsActive(true).stream().map(supplier -> mapper.map(supplier, SupplierDTO.class)).toList();
    }

    @Override
    public SupplierDTO getSupplier(String id) {
        return mapper.map(supplierRepo.findById(id).get(), SupplierDTO.class);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) {
        SuppliersEntity supplier = supplierRepo.findById(supplierDTO.getSupplierCode()).get();
        mapper.map(supplierDTO, supplier);
        SuppliersEntity save = supplierRepo.save(supplier);
        return save != null;
    }

    @Override
    public boolean deleteSupplier(String id) {
        SuppliersEntity supplier = supplierRepo.findById(id).get();
        supplier.setIsActive(false);
        SuppliersEntity save = supplierRepo.save(supplier);
        return save != null;
    }

    @Override
    public List<String> getSupplierId() {
        return supplierRepo.findAllByIsActive(true).stream().map(SuppliersEntity::getSupplierCode).toList();
    }
}
