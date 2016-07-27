package com.brandon.configurations.compressor.processor;

import com.google.common.collect.Lists;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.List;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-06-24.
 */
public class CompressorSearchFiles {
    final Logger logger = LoggerFactory.getLogger(CompressorSearchFiles.class);
    private File root;
    private boolean compressed;
    private ErrorReporter errorReporter = new ErrorReporter() {
        @Override
        public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                logger.warn("{}  {}", sourceName, message);
            } else {
                logger.warn("{}  {}", sourceName, String.format("%s:%s:%s", line, lineOffset, message));
            }
        }

        @Override
        public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                logger.error("{}  {}", sourceName, message);
            } else {
                logger.error("{}  {}", sourceName, String.format("%s:%s:%s", line, lineOffset, message));
            }
        }

        @Override
        public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
            error(message, sourceName, line, lineSource, lineOffset);
            return new EvaluatorException(message);
        }
    };

    public CompressorSearchFiles(File root, boolean compressed) {
        this.root = root;
        this.compressed = compressed;
    }

    public void compressed(CompressorSearchingType searchingType, File output, List<String> filter) {
        switch (searchingType) {
            case CSS:
                compressCss(output, filter);
                break;
            case JS:
                compressJs(output, filter);
                break;
        }
    }

    /**
     * 모든 파일 검색
     *
     * @param result
     * @param root
     * @param searchingType
     */
    private void searchFiles(List<File> result, File root, final CompressorSearchingType searchingType) {
        result.addAll(Lists.newArrayList(root.listFiles(findFile -> findFile.exists() && findFile.isFile() && (searchingType == CompressorSearchingType.CSS ? findFile.getName().endsWith(".css") : findFile.getName().endsWith(".js")))));
        Lists.newArrayList(root.listFiles(pathname -> pathname.isDirectory())).forEach(dir -> searchFiles(result, dir, searchingType));
    }

    private void compressCss(File output, List<String> filter) {
        logger.info("《《《《《 Compressed Css File 》》》》》");
        try {
            Assert.notNull(filter);
            Assert.notEmpty(filter);
        } catch (IllegalArgumentException e) {
            return;
        }
        filter.stream().filter(x -> isFile(x)).forEach(f -> {
            try {
                if (compressed)
                    FileCopyUtils.copy(new BrandonCssCompressor(new InputStreamReader(new FileInputStream(new File(this.root.getPath() + f)), "UTF-8")).compress(-1).getBytes(), new FileOutputStream(output, true));
                else
                    FileCopyUtils.copy(new FileInputStream(new File(this.root.getPath() + f)), new FileOutputStream(output, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void compressJs(File output, List<String> filter) {
        logger.info("《《《《《 Compressed JS File 》》》》》");
        try {
            Assert.notNull(filter);
            Assert.notEmpty(filter);
        } catch (IllegalArgumentException e) {
            return;
        }
        filter.stream().filter(x -> isFile(x)).forEach(f -> {
            try {
                if (f.endsWith(".min.js") || compressed) {
                    FileCopyUtils.copy(new FileInputStream(new File(this.root.getPath() + f)), new FileOutputStream(output, true));
                } else {
                    FileCopyUtils.copy(new BrandonJavaScriptCompressor(new FileReader(new File(this.root.getPath() + f)), errorReporter).compress(-1, true, false, false, false).getBytes(), new FileOutputStream(output, true));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private boolean isFile(String x) {
        File f = new File(this.root.getPath() + x);
        return f.exists() && f.isFile();
    }
}
