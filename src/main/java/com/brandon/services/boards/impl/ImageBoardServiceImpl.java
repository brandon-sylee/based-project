package com.brandon.services.boards.impl;

import com.brandon.configurations.filestorage.StorageService;
import com.brandon.entities.storage.FileEntity;
import com.brandon.repositories.boards.BoardRepository;
import com.brandon.repositories.boards.ContentRepository;
import com.brandon.repositories.storage.FileRepository;
import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.converter.BoardConverter;
import com.brandon.services.boards.models.ImageBoardModel;
import com.brandon.utils.BUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)/* 매우 중요함!! 반드시 선언해야지 TargetClass에 따라서 Service가 구분됨 */
public class ImageBoardServiceImpl extends AbstractBoardService<ImageBoardModel> implements BoardService<ImageBoardModel> {
    private final Logger logger = getLogger(getClass());
    private final StorageService storageService;
    private final FileRepository fileRepository;
    public ImageBoardServiceImpl(BoardRepository repository, ContentRepository contentRepository,
                                 BoardConverter boardConverter, BUtil bUtil,
                                 StorageService storageService,
                                 FileRepository fileRepository) {
        super(repository, contentRepository, boardConverter, bUtil);
        this.storageService = storageService;
        this.fileRepository = fileRepository;
    }

    @Override
    protected Class<ImageBoardModel> getTClass() {
        return ImageBoardModel.class;
    }

    @Override
    public long write(ImageBoardModel imageBoardModel) {
        long mid = super.write(imageBoardModel);
        imageBoardModel.getFiles().parallelStream().filter(x->!x.isEmpty()).forEach(x->{

            storageService.store(x, null);
            fileRepository.save(createFileEntity(mid, x));
        });
        return mid;
    }

    private FileEntity createFileEntity(long mid, MultipartFile file) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalFileName(file.getOriginalFilename());
        fileEntity.setSystemFileName(RandomStringUtils.randomAlphabetic(32));
        fileEntity.setSystemPath(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        fileEntity.setBoardId(mid);
        return fileEntity;
    }
}
