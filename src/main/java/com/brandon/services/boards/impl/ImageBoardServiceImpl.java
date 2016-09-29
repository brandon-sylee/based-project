package com.brandon.services.boards.impl;

import com.brandon.configurations.filestorage.StorageService;
import com.brandon.controllers.statics.ImageController;
import com.brandon.entities.boards.MContentEntity;
import com.brandon.entities.storage.FileEntity;
import com.brandon.repositories.boards.BoardRepository;
import com.brandon.repositories.boards.ContentRepository;
import com.brandon.repositories.storage.FileRepository;
import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.converter.BoardConverter;
import com.brandon.services.boards.models.BoardPageableImpl;
import com.brandon.services.boards.models.ImageBoardModel;
import com.brandon.utils.BUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

    @Transactional(readOnly = true)
    public Page<ImageBoardModel> lists(Pageable pageable, String query) {
        if (StringUtils.hasLength(query)) {
            return search(pageable, query);
        }

        Page<ImageBoardModel> page = getBoardRepository().findAll(pageable).map(source -> getBoardConverter().convertListModel(getTClass(), source));
        if (page.getTotalElements() > 0) {
            List<FileEntity> fileEntities = fileRepository.findAll(page.getContent().parallelStream().flatMapToLong(x -> LongStream.of(x.getMid())).boxed().collect(Collectors.toList()));
            page.getContent().parallelStream().peek(x ->
                    fileEntities.parallelStream().filter(fileEntity -> fileEntity.getMid().equals(x.getMid()))
                            .findFirst().ifPresent(presentImage ->
                            x.setImages(Lists.newArrayList(MvcUriComponentsBuilder.fromMethodName(ImageController.class, "image", presentImage.getMid()).build().toString())))
            ).collect(Collectors.toList());
        }
        return page;
    }

    @Transactional(readOnly = true)
    protected Page<ImageBoardModel> search(Pageable pageable, String query) {
        query = query.trim().replace("\\s{2,}", " ") + "%";
        List<MContentEntity> contentEntities = getContentRepository().findByContentsContainingIgnoreCase(query);
        long total = getBoardRepository().countByContentsInOrSubjectLikeIgnoreCase(contentEntities, query);
        if (total > 0) {
            List<ImageBoardModel> result = getBoardRepository().findByContentsInOrSubjectLikeIgnoreCase(contentEntities, query, pageable).parallelStream()
                    .map(mBoardEntity -> getBoardConverter().convertListModel(getTClass(), mBoardEntity))
                    .collect(Collectors.toList());
            List<FileEntity> fileEntities = fileRepository.findAll(result.parallelStream().flatMapToLong(x -> LongStream.of(x.getMid())).boxed().collect(Collectors.toList()));
            result.parallelStream().peek(x ->
                    fileEntities.parallelStream().filter(fileEntity -> fileEntity.getMid().equals(x.getMid()))
                            .findFirst().ifPresent(presentImage ->
                            x.setImages(Lists.newArrayList(MvcUriComponentsBuilder.fromMethodName(ImageController.class, "image", presentImage.getMid()).build().toString())))
            ).collect(Collectors.toList());
            return new BoardPageableImpl<>(result, pageable, total);
        }
        return new BoardPageableImpl<>(new ArrayList<ImageBoardModel>(), pageable, 0);
    }

    @Override
    public long write(ImageBoardModel imageBoardModel) {
        long mid = super.write(imageBoardModel);
        imageBoardModel.getFiles().parallelStream().filter(x -> !x.isEmpty()).forEach(x -> {
            FileEntity fileEntity = createFileEntity(mid, x);
            storageService.store(x, fileEntity.getSystemPath(), fileEntity.getSystemFileName());
            fileRepository.save(fileEntity);
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
