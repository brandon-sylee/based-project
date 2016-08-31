package com.brandon.configurations.files;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.multipart.UploadedMultipartFile;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URI;
import java.util.LinkedList;
import java.util.Map;

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
    public IntegrationFlow httpFlow(MessageHandler requestGateway) {
        return IntegrationFlows.from("requestChannel")
                .handle(requestGateway)
                .handle(fileLogger())
                .get();
    }

    @Bean
    public DefaultHttpHeaderMapper defaultHttpHeaderMapper() {
        String[] headerNames = new String[] {"Content-Type"};
        DefaultHttpHeaderMapper defaultHttpHeaderMapper = new DefaultHttpHeaderMapper();
        defaultHttpHeaderMapper.setOutboundHeaderNames(headerNames);
        return defaultHttpHeaderMapper;
    }

    public interface MultipartRequestGateway {
        HttpStatus postMultipartRequest(Map<String, Object> multipartRequest);
    }

    public class MultipartReceiver {
        @SuppressWarnings("rawtypes")
        public void receive(LinkedMultiValueMap<String, Object> multipartRequest){
            logger.info("Successfully received multipart request: " + multipartRequest);
            for (String elementName : multipartRequest.keySet()) {
                if (elementName.equals("company")){
                    LinkedList value =  (LinkedList)multipartRequest.get("company");
                    String[] multiValues = (String[]) value.get(0);
                    for (String companyName : multiValues) {
                        logger.info(elementName + " - " + companyName);
                    }
                } else if (elementName.equals("company-logo")){
                    logger.info(elementName + " - as UploadedMultipartFile: "
                            + ((UploadedMultipartFile) multipartRequest.getFirst("company-logo")).getOriginalFilename());
                }
            }
        }
    }
}
