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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepo;

    private final ModelMapper modelMapper;

    private final UploadService uploadService;

    private final SupplierRepository supplierRepo;

    public InventoryServiceImpl(InventoryRepository inventoryRepo, ModelMapper modelMapper, UploadService uploadService, SupplierRepository supplierRepo) {
        this.inventoryRepo = inventoryRepo;
        this.modelMapper = modelMapper;
        this.uploadService = uploadService;
        this.supplierRepo = supplierRepo;
    }


    @Override
    public boolean saveInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException {
        String image = uploadService.uploadFile(file);
        InventoryEntity map = modelMapper.map(inventoryDTO, InventoryEntity.class);
        map.setSupplier(supplierRepo.findById(inventoryDTO.getSupplierId()).get());
        map.setItemPicture(image);
        map.setGetStockTotal(inventoryDTO.getQtyOnHand());
        map.setSupplierName(map.getSupplier().getSupplierName());
        map.setItemCode(IdService.generateItemCode(inventoryDTO.getItemGender(), inventoryDTO.getItemType(), inventoryDTO.getItemDescription()));
        inventoryRepo.save(map);
        return true;
    }

    @Override
    public List<InventoryDTO> getAvailableInventory() {
        return inventoryRepo.findAllByItemStatusNot(ItemStatus.NOT_AVAILABLE).stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class)).toList();
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepo.findAll().stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class)).toList();
    }

    @Override
    public InventoryDTO getInventory(String itemCode) {
        return modelMapper.map(inventoryRepo.findById(itemCode).get(), InventoryDTO.class);
    }

    @Override
    public boolean updateInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException {
        InventoryEntity inventory = inventoryRepo.findById(inventoryDTO.getItemCode()).get();
        inventory.setSupplier(supplierRepo.findById(inventoryDTO.getSupplierId()).get());
        inventory.setSupplierName(inventory.getSupplier().getSupplierName());
        inventory.setSize(inventoryDTO.getSize());
        inventory.setQtyOnHand(inventoryDTO.getQtyOnHand());
        inventory.setSellingPrice(inventoryDTO.getSellingPrice());
        inventory.setItemDescription(inventoryDTO.getItemDescription());
        inventory.setItemGender(inventoryDTO.getItemGender());
        inventory.setItemType(inventoryDTO.getItemType());
        inventory.setBrand(inventoryDTO.getBrand());
        inventory.setBuyingPrice(inventoryDTO.getBuyingPrice());
        inventory.setDiscount(inventoryDTO.getDiscount());
        inventory.setExpectedProfit(inventoryDTO.getExpectedProfit());
        inventory.setProfitMargin(inventoryDTO.getProfitMargin());
        inventory.setItemStatus(inventoryDTO.getItemStatus());
        inventory.setGetStockTotal(inventoryDTO.getQtyOnHand());
        if (!file.getOriginalFilename().equals("notUpdate")) {
            String image = uploadService.uploadFile(file);
            inventory.setItemPicture(image);
        }
        inventoryRepo.save(inventory);
        return true;
    }

    @Override
    public List<String> getBrands() {
        return inventoryRepo.getBrands();
    }

    @Override
    public List<InventoryDTO> getAvailableBrandItems(String brand) {
        return inventoryRepo.findAllByBrandAndItemStatusNot(brand, ItemStatus.NOT_AVAILABLE).stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class)).toList();
    }
}
