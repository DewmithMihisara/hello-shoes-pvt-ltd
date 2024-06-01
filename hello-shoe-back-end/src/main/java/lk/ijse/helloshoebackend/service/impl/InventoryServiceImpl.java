package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.InventoryDTO;
import lk.ijse.helloshoebackend.entity.InventoryEntity;
import lk.ijse.helloshoebackend.enums.ItemStatus;
import lk.ijse.helloshoebackend.repository.InventoryRepository;
import lk.ijse.helloshoebackend.repository.SupplierRepository;
import lk.ijse.helloshoebackend.service.InventoryService;
import lk.ijse.helloshoebackend.service.UploadService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final UploadService uploadService;
    private final SupplierRepository supplierRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ModelMapper modelMapper, UploadService uploadService, SupplierRepository supplierRepository) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.uploadService = uploadService;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public boolean saveInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException {
        logger.info("Saving inventory for item: {}", inventoryDTO.getItemDescription());
        String image = uploadService.uploadFile(file);
        InventoryEntity map = modelMapper.map(inventoryDTO, InventoryEntity.class);
        map.setSupplierEntity(supplierRepository.findById(inventoryDTO.getSupplierId()).get());
        map.setItemPicture(image);
        map.setGetStockTotal(inventoryDTO.getQtyOnHand());
        map.setSupplierName(map.getSupplierEntity().getSupplierName());
        map.setItemCode(IdService.generateItemCode(inventoryDTO.getItemGender(), inventoryDTO.getItemType(), inventoryDTO.getItemDescription()));
        inventoryRepository.save(map);
        logger.info("InventoryEntity saved with item code: {}", map.getItemCode());
        return true;
    }

    @Override
    public List<InventoryDTO> getAvailableInventory() {
        logger.info("Fetching available inventory");
        return inventoryRepository.findAllByItemStatusNot(ItemStatus.NOT_AVAILABLE)
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
                .toList();
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        logger.info("Fetching all inventory");
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
                .toList();
    }

    @Override
    public InventoryDTO getInventory(String itemCode) {
        logger.info("Fetching inventory for item code: {}", itemCode);
        return modelMapper.map(inventoryRepository.findById(itemCode).get(), InventoryDTO.class);
    }

    @Override
    public boolean updateInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException {
        logger.info("Updating inventoryEntity for item code: {}", inventoryDTO.getItemCode());
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryDTO.getItemCode()).get();
        inventoryEntity.setSupplierEntity(supplierRepository.findById(inventoryDTO.getSupplierId()).get());
        inventoryEntity.setSupplierName(inventoryEntity.getSupplierEntity().getSupplierName());
        inventoryEntity.setSize(inventoryDTO.getSize());
        inventoryEntity.setQtyOnHand(inventoryDTO.getQtyOnHand());
        inventoryEntity.setSellingPrice(inventoryDTO.getSellingPrice());
        inventoryEntity.setItemDescription(inventoryDTO.getItemDescription());
        inventoryEntity.setItemGender(inventoryDTO.getItemGender());
        inventoryEntity.setItemType(inventoryDTO.getItemType());
        inventoryEntity.setBrand(inventoryDTO.getBrand());
        inventoryEntity.setBuyingPrice(inventoryDTO.getBuyingPrice());
        inventoryEntity.setDiscount(inventoryDTO.getDiscount());
        inventoryEntity.setExpectedProfit(inventoryDTO.getExpectedProfit());
        inventoryEntity.setProfitMargin(inventoryDTO.getProfitMargin());
        inventoryEntity.setItemStatus(inventoryDTO.getItemStatus());
        inventoryEntity.setGetStockTotal(inventoryDTO.getQtyOnHand());
        if (!file.getOriginalFilename().equals("notUpdate")) {
            String image = uploadService.uploadFile(file);
            inventoryEntity.setItemPicture(image);
        }
        inventoryRepository.save(inventoryEntity);
        logger.info("InventoryEntity updated for item code: {}", inventoryDTO.getItemCode());
        return true;
    }

    @Override
    public List<String> getBrands() {
        logger.info("Fetching all brands");
        return inventoryRepository.getBrands();
    }

    @Override
    public List<InventoryDTO> getAvailableBrandItems(String brand) {
        logger.info("Fetching available items for brand: {}", brand);
        return inventoryRepository.findAllByBrandAndItemStatusNot(brand, ItemStatus.NOT_AVAILABLE)
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
                .toList();
    }
}