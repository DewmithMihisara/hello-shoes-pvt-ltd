package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.InventoryQtyDTO;
import lk.ijse.helloshoebackend.dto.SaleInventoryDTO;
import lk.ijse.helloshoebackend.repository.SaleInventoryRepository;
import lk.ijse.helloshoebackend.service.SaleInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleInventoryServiceImpl implements SaleInventoryService {
    @Autowired
    private SaleInventoryRepository saleInventoryRepository;

    public SaleInventoryDTO getSaleInventoryDataBySaleId(String saleId) {
        List<Object[]> results = saleInventoryRepository.findInventoryQtyBySaleId(saleId);
        List<InventoryQtyDTO> inventoryQtyList = results.stream()
                .map(result -> new InventoryQtyDTO((String) result[0], (Integer) result[1]))
                .collect(Collectors.toList());
        return new SaleInventoryDTO(saleId, inventoryQtyList);
    }



}
