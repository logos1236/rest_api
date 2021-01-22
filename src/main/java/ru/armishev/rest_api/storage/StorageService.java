package ru.armishev.rest_api.storage;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class StorageService implements IStorageService {
    //private static Path uploadCatalog = Paths.get("src","main","resources", "upload");
    private final Path uploadCatalog;

    @Autowired
    public StorageService(FileStorageProperties fileStorageProperties) {
        this.uploadCatalog = Paths.
                get(fileStorageProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.uploadCatalog);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String load(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File newFile = createDestinationFile(file);
            FileUtils.writeByteArrayToFile(newFile, file.getBytes());

            return newFile.getAbsolutePath();
        } else {
            throw new IOException("Download file is empty");
        }
    }

    /* Создаем новый файл, для записи */
    private File createDestinationFile(MultipartFile file) throws IOException {
        File newFile = null;

        if (!file.isEmpty()) {
            File newDestinationDirectory = createNewUnicDestinationDirectory();

            newFile = new File(new StringBuilder(newDestinationDirectory.getAbsolutePath())
                    .append(File.separator)
                    .append(file.getOriginalFilename())
                    .toString());
        } else {
            throw new IOException("Download file is empty");
        }

        return newFile;
    }

    /* Создаем каталог для хранения файлов */
    private File createNewUnicDestinationDirectory() throws IOException {
        String newCatalogName = generatingRandomAlphanumericString();
        String fileNameBuilder = uploadCatalog+File.separator;
        File newDestinationDirectory = new File(new StringBuilder(fileNameBuilder).append(newCatalogName).toString());
        while (newDestinationDirectory.exists()) {
            newCatalogName = generatingRandomAlphanumericString();
            newDestinationDirectory = new File(new StringBuilder(fileNameBuilder).append(newCatalogName).toString());
        }

        if (!newDestinationDirectory.mkdirs()) {
            throw new IOException("Can't create destination directory");
        }

        return newDestinationDirectory;
    }

    private String generatingRandomAlphanumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
