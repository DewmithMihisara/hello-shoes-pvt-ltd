package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.InventoryDTO;
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
public interface InventoryService {
    boolean saveInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException;

    List<InventoryDTO> getAvailableInventory();

    List<InventoryDTO> getAllInventory();

    InventoryDTO getInventory(String itemCode);

    boolean updateInventory(InventoryDTO inventoryDTO, MultipartFile file) throws IOException;

    List<String> getBrands();

    List<InventoryDTO> getAvailableBrandItems(String brand);
}
