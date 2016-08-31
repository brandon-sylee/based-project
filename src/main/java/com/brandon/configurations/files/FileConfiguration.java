package com.brandon.configurations.files;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Transformers;

import java.io.File;

/**
 * Created by brandon Lee on 2016-08-31.
 */
@Configuration
public class FileConfiguration {
    private final String INBOUND_PATH = "d:/";

    /*@Bean
    public IntegrationFlow fileReadingFlow() {
        return IntegrationFlows
                .from(s -> s.file(new File(INBOUND_PATH)).patternFilter("*.txt"), e -> e.poller(Pollers.fixedDelay(1000)))
                .transform(Transformers.fileToString())
                .channel("processFileChannel")
                .get();
    }*/
}
