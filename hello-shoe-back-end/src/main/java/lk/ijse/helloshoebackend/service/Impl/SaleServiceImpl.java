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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepo;
    private final ModelMapper mapper;
    private final InventoryRepository inventoryRepo;
    private final SaleInventoryRepository saleinventoryRepo;
    private final CustomerRepository customerRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public boolean saveSale(SaleDTO saleDTO) {
        List<InventoryEntity> inventories = new ArrayList<>();
        SaleEntity sale = mapper.map(saleDTO, SaleEntity.class);
        saleDTO.getInventories().forEach(inventory -> inventoryRepo.findById(inventory.getItemCode()).ifPresent(entity -> {
            entity.setQtyOnHand(entity.getQtyOnHand() - inventory.getGetqty());
            entity.setItemSoldCount(entity.getItemSoldCount() + inventory.getGetqty());
            Integer getStockTotal = entity.getGetStockTotal();
            int percentageInStock = (entity.getQtyOnHand() * 100) / getStockTotal;
            if (percentageInStock <= 50 && entity.getQtyOnHand() >= 1) {
                entity.setItemStatus(ItemStatus.LOW_STOCK);
            } else if (entity.getQtyOnHand() == 0) {
                entity.setItemStatus(ItemStatus.NOT_AVAILABLE);
            }
            inventories.add(inventoryRepo.save(entity));
        }));
        String saleID = IdService.generateID(Id.SALE_ID);
        sale.setSaleId(saleID);
        sale.setInventories(inventories);
        sale.setUser(userRepo.findById(saleDTO.getCashierName()).get());
        sale.setSubTotal(saleDTO.getSubTotal());
        if (!saleDTO.getIsDemo()) {
            CustomerEntity customer = customerRepo.findCustomerByContact(saleDTO.getCustomerContact());
            customer.setTotalPoints(customer.getTotalPoints() == null ? saleDTO.getAddedPoints() : customer.getTotalPoints() + saleDTO.getAddedPoints());
            int totalPoints = customer.getTotalPoints() + saleDTO.getAddedPoints();
            if (totalPoints < 50) {
                customer.setLevel(Level.NEW);
            } else if (totalPoints < 100) {
                customer.setLevel(Level.BRONZE);
            } else if (totalPoints < 200) {
                customer.setLevel(Level.SILVER);
            } else {
                customer.setLevel(Level.GOLD);
            }
            customer.setRecentPurchaseDate(new Date(System.currentTimeMillis()));
            sale.setCustomer(customerRepo.save(customer));
        } else {
            sale.setCustomer(null);
        }
        saleRepo.save(sale);

        saleDTO.getInventories().forEach(inventory -> {
            saleinventoryRepo.updateSaleInventoryQty(
                    saleID,
                    inventory.getItemCode(),
                    inventory.getGetqty()
            );
            System.out.println(saleID + " " + inventory.getItemCode() + " " + inventory.getGetqty());

        });
        return true;
    }

    @Override
    @Transactional
    public boolean updateSale(SaleInventoryCollectionDTO saleInventoryCollectionDTO) {
        Optional<SaleEntity> saleOptional = saleRepo.findById(saleInventoryCollectionDTO.getSaleDTO().getSaleId());
        SaleEntity existingSale = saleOptional.orElseThrow(() -> new RuntimeException("Sale not found with id: " + saleInventoryCollectionDTO.getSaleDTO().getSaleId()));
        existingSale.setSubTotal(existingSale.getSubTotal()-saleInventoryCollectionDTO.getSaleDTO().getSubTotal());
        saleRepo.save(existingSale);

        System.out.println("sale done");
        saleInventoryCollectionDTO.getInventoryDTOList().forEach(inventoryDTO -> {
            System.out.println(inventoryDTO.toString());
            Optional<InventoryEntity> invetoryOptional = inventoryRepo.findById(inventoryDTO.getItemCode());
            System.out.println(invetoryOptional.stream().toList());
            InventoryEntity existingInvent= invetoryOptional.orElseThrow(() -> new RuntimeException("Item not found with id: " + inventoryDTO.getItemCode()));
            System.out.println(existingInvent.toString());
            inventoryRepo.updateInventory(inventoryDTO.getItemCode(),existingInvent.getItemSoldCount() - inventoryDTO.getItemSoldCount(), existingInvent.getQtyOnHand() + inventoryDTO.getItemSoldCount());
        });

        System.out.println("invent done");

        System.out.println(saleInventoryCollectionDTO.getSaleInventoryDTO());
        saleInventoryCollectionDTO.getSaleInventoryDTO().getInventoryDetails().forEach(inventoryQtyDTO -> {
            System.out.println(inventoryQtyDTO);
            saleinventoryRepo.updateSaleInventoryQty(saleInventoryCollectionDTO.getSaleDTO().getSaleId(), inventoryQtyDTO.getInventory_id(), inventoryQtyDTO.getQty());
        });

        System.out.println("sale invent done");


        return true;
    }


    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepo.findAll().stream().map(sale -> mapper.map(sale, SaleDTO.class)).toList();
    }
}
