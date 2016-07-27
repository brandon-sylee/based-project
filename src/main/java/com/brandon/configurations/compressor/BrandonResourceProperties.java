package com.brandon.configurations.compressor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-06-30.
 */
@ConfigurationProperties(locations = "classpath:resources.yml", prefix = "brandon.resources")
@Data
public class BrandonResourceProperties {
    private boolean compressed;
    private boolean using;
    private List<String> js;
    private List<String> css;
}
