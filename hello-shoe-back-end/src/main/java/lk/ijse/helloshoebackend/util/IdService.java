package lk.ijse.helloshoebackend.util;

import org.springframework.stereotype.Service;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class IdService {

    public static String generateID(Constants constants) {
        return switch (constants) {
            case USER_ID -> "USR-" + System.currentTimeMillis();
            case SUPPLIER_ID -> "SUP-" + System.currentTimeMillis();
            default -> "EMP-" + System.currentTimeMillis();
        };
    }
}
