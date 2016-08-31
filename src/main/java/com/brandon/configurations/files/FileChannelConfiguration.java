package com.brandon.configurations.files;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @link "https://github.com/spring-projects/spring-integration-samples/blob/master/intermediate/multipart-http/src/main/resources/META-INF/spring/integration/http-outbound-config.xml"
 * Created by brandon Lee on 2016-08-31.
 */
@Configuration
public class FileChannelConfiguration {
    private final Logger logger = getLogger(getClass());

    @Bean
    public MessageHandler fileLogger() {
        LoggingHandler loggingHandler = new LoggingHandler("INFO");
        loggingHandler.setLoggerName("FileChannelConfiguration");
        return loggingHandler;
    }

    @Bean
    public MessageHandler requestGateway(/*@Value("${api.base.uri}/data") URI uri*/) {
        URI uri = URI.create("http://localhost:8080/api/upload");
        return Http
                .outboundGateway(uri)
                .httpMethod(HttpMethod.POST)
                .extractPayload(true)
                .headerMapper(defaultHttpHeaderMapper())
                .get();
    }

    @Bean
    public HttpRequestHandler httpInboundAdapter() {
        return Http
                .inboundChannelAdapter("/api/upload")
                .requestMapping(requestMappingSpec -> requestMappingSpec.methods(HttpMethod.GET, HttpMethod.POST))
                .requestChannel("receiveChannel")
                .multipartResolver(commonsMultipartResolver())
                .get();
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // TODO 업로드 설정
        return commonsMultipartResolver;
    }

    @Bean
    public IntegrationFlow httpFlow(MessageHandler requestGateway) {
        return IntegrationFlows.from("requestChannel")
                .handle(requestGateway)
                .handle(fileLogger())
                .get();
    }

    @Bean
    public DefaultHttpHeaderMapper defaultHttpHeaderMapper() {
        String[] headerNames = new String[]{"Content-Type"};
        DefaultHttpHeaderMapper defaultHttpHeaderMapper = new DefaultHttpHeaderMapper();
        defaultHttpHeaderMapper.setOutboundHeaderNames(headerNames);
        return defaultHttpHeaderMapper;
    }
}
