package lk.ijse.helloshoebackend.util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Service
public class DbService {
    private final JdbcTemplate jdbcTemplate;

    public DbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addQtyColumnToSaleInventory() {
        String sql = "ALTER TABLE sale_inventory ADD COLUMN qty INT NOT NULL DEFAULT 0";
        jdbcTemplate.execute(sql);
    }
}
