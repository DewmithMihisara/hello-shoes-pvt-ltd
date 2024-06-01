package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.SupplierDTO;
import lk.ijse.helloshoebackend.entity.SupplierEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.repository.SupplierRepository;
import lk.ijse.helloshoebackend.service.SupplierService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private final SupplierRepository supplierRepository;

    private final ModelMapper mapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setSupplierCode(IdService.generateID(Id.SUPPLIER_ID));
        supplierDTO.setIsActive(true);
        SupplierEntity save = supplierRepository.save(mapper.map(supplierDTO, SupplierEntity.class));
        logger.info("SupplierEntity saved: {}", save);
        return save != null;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierEntity> supplierEntities = supplierRepository.findAllByIsActive(true);
        List<SupplierDTO> supplierDTOs = supplierEntities.stream()
                .map(supplierEntity -> mapper.map(supplierEntity, SupplierDTO.class))
                .toList();
        logger.info("Retrieved {} active supplierEntities", supplierEntities.size());
        return supplierDTOs;
    }

    @Override
    public SupplierDTO getSupplier(String id) {
        logger.info("Retrieving supplier with ID: {}", id);
        return supplierRepository.findById(id)
                .map(supplierEntity -> mapper.map(supplierEntity, SupplierDTO.class))
                .orElseThrow(() -> {
                    logger.error("SupplierEntity with ID {} not found", id);
                    return new NoSuchElementException("SupplierEntity with ID " + id + " not found");
                });
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) {
        SupplierEntity supplierEntity = supplierRepository.findById(supplierDTO.getSupplierCode()).get();
        mapper.map(supplierDTO, supplierEntity);
        SupplierEntity save = supplierRepository.save(supplierEntity);
        logger.info("SupplierEntity updated: {}", save);
        return save != null;
    }

    @Override
    public boolean deleteSupplier(String id) {
        SupplierEntity supplierEntity = supplierRepository.findById(id).get();
        supplierEntity.setIsActive(false);
        SupplierEntity save = supplierRepository.save(supplierEntity);
        logger.info("SupplierEntity deleted: {}", save);
        return save != null;
    }

    @Override
    public List<String> getSupplierId() {
        List<String> supplierIds = supplierRepository.findAllByIsActive(true).stream()
                .map(SupplierEntity::getSupplierCode)
                .toList();
        logger.info("Retrieved {} active supplier IDs", supplierIds.size());
        return supplierIds;
    }
}