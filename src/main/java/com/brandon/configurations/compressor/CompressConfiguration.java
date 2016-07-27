package com.brandon.configurations.compressor;

import com.brandon.configurations.compressor.processor.CompressorSearchFiles;
import com.brandon.configurations.compressor.processor.CompressorSearchingType;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-06-30.
 */
@Configuration
@EnableConfigurationProperties(BrandonResourceProperties.class)
public class CompressConfiguration implements InitializingBean, DisposableBean {
    final Logger logger = getLogger(CompressConfiguration.class);
    private final String CSS_LASTEST = "/latest.css";
    private final String JS_LASTEST = "/latest.js";
    @Autowired
    BrandonResourceProperties resourceProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (resourceProperties.isUsing()) {
            try {
                File resourcesFolder = new File(ResourceUtils.getURL("classpath:static").getPath());
                File css = new File(resourcesFolder.getPath() + CSS_LASTEST);
                initFile(css);
                File js = new File(resourcesFolder.getPath() + JS_LASTEST);
                initFile(js);

                CompressorSearchFiles compressorSearchFiles = new CompressorSearchFiles(resourcesFolder, resourceProperties.isCompressed());
                compressorSearchFiles.compressed(CompressorSearchingType.CSS, css, resourceProperties.getCss());
                compressorSearchFiles.compressed(CompressorSearchingType.JS, js, resourceProperties.getJs());
            } catch (IOException e) {
                logger.error("《《《《《 Do not find resource file for compressed. 》》》》》");
            }
        } else {
            logger.info("《《《《《 Do not used resource file for compressed. 》》》》》");
        }
    }


    private void initFile(File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    @Override
    public void destroy() throws Exception {
        File resourcesFolder = new File(ResourceUtils.getURL("classpath:static").getPath());
        File css = new File(resourcesFolder.getPath() + CSS_LASTEST);
        File js = new File(resourcesFolder.getPath() + JS_LASTEST);
        if (css.exists()) css.delete();
        if (js.exists()) js.delete();
    }
}
