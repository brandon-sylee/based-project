package com.brandon.controllers.statics;

import com.brandon.configurations.filestorage.StorageFileNotFoundException;
import com.brandon.configurations.filestorage.StorageService;
import com.brandon.entities.storage.FileEntity;
import com.brandon.repositories.storage.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by brandon Lee on 2016-09-29.
 */
@Controller
@RequestMapping("rs")
@RequiredArgsConstructor
public class ImageController {
    private final StorageService storageService;
    private final FileRepository fileRepository;

    @GetMapping("img/{imageMid:\\d+}")
    public ResponseEntity<Resource> image(@PathVariable Long imageMid) {
        Optional<FileEntity> exist = Optional.ofNullable(fileRepository.findOne(imageMid));
        if ( exist.isPresent() ) {
            FileEntity image = exist.get();
            Resource file = storageService.load(image.getSystemPath(), image.getSystemFileName());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        }
        return ResponseEntity
                .badRequest()
                .body(null);
    }
}
