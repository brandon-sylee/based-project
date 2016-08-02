package com.brandon.utils;

import com.brandon.BasedProjectProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by Naver on 2016-07-20.
 */
@Component
public class BUtil {
    @Autowired
    private Environment environment;
    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        objectMapper = new ObjectMapper();
    }

    public boolean isRealMode() {
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(x -> x.equals(BasedProjectProfiles.staging.name()) || x.equals(BasedProjectProfiles.production.name()));
    }


    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    public String prettyPrinter(Object obj) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
