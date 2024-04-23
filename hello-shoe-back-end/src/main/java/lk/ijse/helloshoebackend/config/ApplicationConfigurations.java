package lk.ijse.helloshoebackend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
@Configuration
public class ApplicationConfigurations {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
