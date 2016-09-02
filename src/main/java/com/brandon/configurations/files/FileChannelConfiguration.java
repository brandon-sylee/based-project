package com.brandon.configurations.files;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.multipart.UploadedMultipartFile;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @link "https://github.com/spring-projects/spring-integration-samples/blob/master/intermediate/multipart-http/src/main/resources/META-INF/spring/integration/http-outbound-config.xml"
 * Created by brandon Lee on 2016-08-31.
 */
@Configuration
public class FileChannelConfiguration {

    private final Logger logger = getLogger(getClass());
    private final String directory = "upload-dir";
    private File ROOT;


    @PostConstruct
    public void init() throws IOException {
        this.ROOT = new File(directory);
        FileSystemUtils.deleteRecursively(ROOT);
        Files.createDirectory(Paths.get(directory));
    }


    /**
     * URL을 매핑해줌.
     *
     * @return
     */
    @Bean
    public HttpRequestHandlingMessagingGateway inboundChannelAdapter() {
        return Http
                .inboundChannelAdapter("/api/upload")
                .requestChannel(fileUploadChannel())
                .requestMapping(spec -> spec.methods(HttpMethod.GET, HttpMethod.POST))
                .get();
    }

    /**
     * 위에 설정된 url로 업로드된 파일을 필터링해서 서버에 업로드한다(ftp 등을 연동할 수 있다)
     * @return
     */
    @Bean
    public MessageChannel fileUploadChannel() {
        DirectChannel receiveChannel = MessageChannels.direct("fileUploadChannel").get();
        receiveChannel.subscribe(message -> Stream.of(message)
                .map(x -> (LinkedMultiValueMap) x.getPayload())
                .forEach(x ->
                        x.values()
                                .parallelStream()
                                .filter(v -> v instanceof LinkedList)
                                .flatMap(item -> ((LinkedList) item).parallelStream().filter(file -> file instanceof UploadedMultipartFile))
                                .forEach(file -> {
                                    try {
                                        // TODO 디비에 파일 이름을 저장.
                                        ((UploadedMultipartFile) file).transferTo(File.createTempFile(RandomStringUtils.randomAlphanumeric(64), null, ROOT));
                                    } catch (IOException e) {
                                        logger.error("File Upload Exception!!!", e);
                                    }
                                })
                ));
        return receiveChannel;
    }
}
