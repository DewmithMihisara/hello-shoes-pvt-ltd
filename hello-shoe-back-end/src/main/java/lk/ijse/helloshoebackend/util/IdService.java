package lk.ijse.helloshoebackend.util;

import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.enums.ItemGender;
import lk.ijse.helloshoebackend.enums.ItemType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class IdService {

    public static String generateID(Id constants) {
        return switch (constants) {
            case BRANCH_ID -> "BR-" + System.currentTimeMillis()+ LocalDate.now();
            case SUPPLIER_ID -> "SUP-" + System.currentTimeMillis()+ LocalDate.now();
            case CUSTOMER_ID -> "CUS-" + System.currentTimeMillis()+ LocalDate.now();
            case SALE_ID -> "SAL-" + System.currentTimeMillis()+ LocalDate.now();
            case EMPLOYEE_ID -> "EMP-" + System.currentTimeMillis()+ LocalDate.now();
            default -> "EMP-" + System.currentTimeMillis()+ LocalDate.now();
        };
    }

    public static String generateItemCode(ItemGender itemGender, ItemType itemType, String itemDescription) {
        String uuid = UUID.randomUUID().toString();
        return String.format("%s%s%s-%s",itemType.toString().charAt(0),"S", itemGender.toString().charAt(0), uuid.substring(0, 6));
    }
}
