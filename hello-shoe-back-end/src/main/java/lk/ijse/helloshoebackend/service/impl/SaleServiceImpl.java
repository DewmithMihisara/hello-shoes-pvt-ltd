package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.SaleDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryCollectionDTO;
import lk.ijse.helloshoebackend.entity.CustomerEntity;
import lk.ijse.helloshoebackend.entity.InventoryEntity;
import lk.ijse.helloshoebackend.entity.SaleEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.enums.ItemStatus;
import lk.ijse.helloshoebackend.enums.Level;
import lk.ijse.helloshoebackend.repository.*;
import lk.ijse.helloshoebackend.service.SaleService;
import lk.ijse.helloshoebackend.util.IdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;
    private final SaleInventoryRepository saleinventoryRepository;
    private final ModelMapper mapper;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean saveSale(SaleDTO saleDTO) {
        List<InventoryEntity> inventories = new ArrayList<>();
        SaleEntity saleEntity = mapper.map(saleDTO, SaleEntity.class);
        saleDTO.getInventories().forEach(inventory -> inventoryRepository.findById(inventory.getItemCode()).ifPresent(entity -> {
            entity.setQtyOnHand(entity.getQtyOnHand() - inventory.getGetqty());
            entity.setItemSoldCount(entity.getItemSoldCount() + inventory.getGetqty());
            Integer getStockTotal = entity.getGetStockTotal();
            int percentageInStock = (entity.getQtyOnHand() * 100) / getStockTotal;
            if (percentageInStock <= 50 && entity.getQtyOnHand() >= 1) {
                entity.setItemStatus(ItemStatus.LOW_STOCK);
            } else if (entity.getQtyOnHand() == 0) {
                entity.setItemStatus(ItemStatus.NOT_AVAILABLE);
            }
            inventories.add(inventoryRepository.save(entity));
        }));
        String saleID = IdService.generateID(Id.SALE_ID);
        saleEntity.setSaleId(saleID);
        saleEntity.setInventories(inventories);
        saleEntity.setUserEntity(userRepository.findById(saleDTO.getCashierName()).get());
        saleEntity.setSubTotal(saleDTO.getSubTotal());
        if (!saleDTO.getIsDemo()) {
            CustomerEntity customerEntity = customerRepository.findCustomerByContact(saleDTO.getCustomerContact());
            customerEntity.setTotalPoints(customerEntity.getTotalPoints() == null ? saleDTO.getAddedPoints() : customerEntity.getTotalPoints() + saleDTO.getAddedPoints());
            int totalPoints = customerEntity.getTotalPoints() + saleDTO.getAddedPoints();
            if (totalPoints < 50) {
                customerEntity.setLevel(Level.NEW);
            } else if (totalPoints < 100) {
                customerEntity.setLevel(Level.BRONZE);
            } else if (totalPoints < 200) {
                customerEntity.setLevel(Level.SILVER);
            } else {
                customerEntity.setLevel(Level.GOLD);
            }
            customerEntity.setRecentPurchaseDate(new Date(System.currentTimeMillis()));
            saleEntity.setCustomerEntity(customerRepository.save(customerEntity));
        } else {
            saleEntity.setCustomerEntity(null);
        }
        saleRepository.save(saleEntity);

        saleDTO.getInventories().forEach(inventory -> {
            saleinventoryRepository.updateSaleInventoryQty(
                    saleID,
                    inventory.getItemCode(),
                    inventory.getGetqty()
            );
            logger.info("{} {} {}", saleID, inventory.getItemCode(), inventory.getGetqty());

        });
        return true;
    }

    @Override
    @Transactional
    public boolean updateSale(SaleInventoryCollectionDTO saleInventoryCollectionDTO) {
        Optional<SaleEntity> saleOptional = saleRepository.findById(saleInventoryCollectionDTO.getSaleDTO().getSaleId());
        SaleEntity existingSaleEntity = saleOptional.orElseThrow(() -> new RuntimeException("SaleEntity not found with id: " + saleInventoryCollectionDTO.getSaleDTO().getSaleId()));
        existingSaleEntity.setSubTotal(existingSaleEntity.getSubTotal()-saleInventoryCollectionDTO.getSaleDTO().getSubTotal());
        saleRepository.save(existingSaleEntity);

        logger.info("SaleEntity update done");
        saleInventoryCollectionDTO.getInventoryDTOList().forEach(inventoryDTO -> {
            logger.info(inventoryDTO.toString());
            Optional<InventoryEntity> invetoryOptional = inventoryRepository.findById(inventoryDTO.getItemCode());
            logger.info(invetoryOptional.toString());
            InventoryEntity existingInvent= invetoryOptional.orElseThrow(() -> new RuntimeException("Item not found with id: " + inventoryDTO.getItemCode()));
            logger.info(existingInvent.toString());
            inventoryRepository.updateInventory(inventoryDTO.getItemCode(),existingInvent.getItemSoldCount() - inventoryDTO.getItemSoldCount(), existingInvent.getQtyOnHand() + inventoryDTO.getItemSoldCount());
        });

        logger.info("InventoryEntity update done");

        logger.info(saleInventoryCollectionDTO.getSaleInventoryDTO().toString());
        saleInventoryCollectionDTO.getSaleInventoryDTO().getInventoryDetails().forEach(inventoryQtyDTO -> {
            logger.info(inventoryQtyDTO.toString());
            saleinventoryRepository.updateSaleInventoryQty(saleInventoryCollectionDTO.getSaleDTO().getSaleId(), inventoryQtyDTO.getInventory_id(), inventoryQtyDTO.getQty());
        });

        logger.info("SaleEntity inventory update done");

        return true;
    }


    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream().map(sale -> mapper.map(sale, SaleDTO.class)).toList();
    }

}