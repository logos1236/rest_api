package ru.armishev.rest_api.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {
    String load(MultipartFile file) throws IOException;
}
