package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.SaleDTO;
import lk.ijse.helloshoebackend.entity.CustomerEntity;
import lk.ijse.helloshoebackend.entity.InventoryEntity;
import lk.ijse.helloshoebackend.entity.SaleEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.enums.ItemStatus;
import lk.ijse.helloshoebackend.enums.Level;
import lk.ijse.helloshoebackend.repository.CustomerRepository;
import lk.ijse.helloshoebackend.repository.InventoryRepository;
import lk.ijse.helloshoebackend.repository.SaleRepository;
import lk.ijse.helloshoebackend.repository.UserRepository;
import lk.ijse.helloshoebackend.service.SaleService;
import lk.ijse.helloshoebackend.util.IdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        sale.setSaleId(IdService.generateID(Id.SALE_ID));
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
        return true;
    }
}
