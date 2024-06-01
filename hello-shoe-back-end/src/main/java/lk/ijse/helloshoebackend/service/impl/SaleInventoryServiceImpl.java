package lk.ijse.helloshoebackend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.ijse.helloshoebackend.dto.InventoryQtyDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryAllDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryDTO;
import lk.ijse.helloshoebackend.repository.SaleInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class SaleInventoryServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SaleInventoryServiceImpl.class);

    @Autowired
    private SaleInventoryRepository saleInventoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SaleInventoryDTO getSaleInventoryDataBySaleId(String saleId) {
        logger.info("Fetching sale inventory data for sale ID: {}", saleId);
        List<Object[]> results = saleInventoryRepository.findInventoryQtyBySaleId(saleId);
        List<InventoryQtyDTO> inventoryQtyList = results.stream()
                .map(result -> new InventoryQtyDTO((String) result[0], (Integer) result[1]))
                .collect(Collectors.toList());
        SaleInventoryDTO saleInventory = new SaleInventoryDTO(saleId, inventoryQtyList);
        logger.info("SaleEntity inventory data fetched for sale ID: {}", saleId);
        return saleInventory;
    }

    public List<SaleInventoryAllDTO> getAllSales() {
        logger.info("Fetching all sales inventory data");
        List<Object[]> results = entityManager.createNativeQuery("SELECT sale_id, inventory_id, qty FROM sale_inventory").getResultList();
        List<SaleInventoryAllDTO> salesInventoryList = results.stream()
                .map(result -> new SaleInventoryAllDTO((String) result[0], (String) result[1], (int) result[2]))
                .collect(Collectors.toList());
        logger.info("All sales inventory data fetched");
        return salesInventoryList;
    }
}