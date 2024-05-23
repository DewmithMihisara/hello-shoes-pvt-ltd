package lk.ijse.helloshoebackend.service.impl;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import lk.ijse.helloshoebackend.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class UploadServiceImpl implements UploadService {

    private final Drive drive;

    public UploadServiceImpl(Drive drive) {
        this.drive = drive;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("helloShoe", "images");
        file.transferTo(tempFile);
        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        fileMetaData.setName(tempFile.getName());
        fileMetaData.setParents(Collections.singletonList("1bdT4xBT02HmERcIZAoy9yDAUyIr1Mekq"));
        FileContent mediaContent = new FileContent("image/jpeg", tempFile);
        com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                .setFields("id").execute();
        tempFile.delete();
        return uploadedFile.getId();
    }
}
