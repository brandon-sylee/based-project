package com.brandon.utils;

import com.brandon.BasedProjectProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Naver on 2016-07-20.
 */
@Component
@RequiredArgsConstructor
public class BUtil {
    private final Environment environment;
    private ObjectMapper objectMapper;
    private Random random;

    @PostConstruct
    private void init() {
        objectMapper = new ObjectMapper();
        random = new Random();
    }

    public boolean isRealMode() {
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(x -> x.equals(BasedProjectProfiles.staging.name()) || x.equals(BasedProjectProfiles.production.name()));
    }


    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    public String prettyPrinter(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
