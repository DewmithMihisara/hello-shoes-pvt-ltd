package lk.ijse.helloshoebackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public interface UploadService {
    String uploadFile(MultipartFile file) throws IOException;
}
