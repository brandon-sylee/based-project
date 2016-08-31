package com.brandon.configurations.files;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.messaging.MessageChannel;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @link https://github.com/spring-projects/spring-integration-samples/blob/master/intermediate/multipart-http/src/main/resources/META-INF/spring/integration/http-outbound-config.xml
 * Created by brandon Lee on 2016-08-31.
 */
@Configuration
public class FileConfiguration {
    private final Logger logger = getLogger(getClass());
    private final String INBOUND_PATH = "d:/";

    /**
     * 파일 다운로드 Flow
     * @return
     */
    @Bean
    public IntegrationFlow downloadFlow() {
        IntegrationFlows
                .from("downloadChannel")
                .get();
        return null;
    }

    /**
     * 파일 업로드 Flow
     * @return
     */
    @Bean
    public IntegrationFlow uploadFlow() {
        return null;
    }
}
