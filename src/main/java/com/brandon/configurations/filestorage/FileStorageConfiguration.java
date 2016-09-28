package com.brandon.configurations.filestorage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@EnableConfigurationProperties(StorageProperties.class)
@Configuration
public class FileStorageConfiguration {
}
