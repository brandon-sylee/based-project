package com.brandon.configurations.filestorage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void store(MultipartFile file, String path, String randomFileName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource load(String path, String fileName);

    Resource loadAsResource(String filename);

    void deleteAll();

}
