package ktb3.full.community.service;

import ktb3.full.community.common.exception.ImageUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${file.path.base}")
    private String fileBasePath;

    @Value("${file.path.image}")
    private String fileImagePath;

    public String saveImageAndGetName(MultipartFile image) {
        String imageName = null;

        if (image != null) {
            imageName = this.fileImagePath + "/" + UUID.randomUUID() + "_" + image.getOriginalFilename();;
            Path path = Paths.get(this.fileBasePath + imageName);
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                throw new ImageUploadFailedException();
            }
        }

        return imageName;
    }
}
