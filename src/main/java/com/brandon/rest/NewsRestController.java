package com.brandon.rest;

import com.brandon.rest.exceptions.NewFeedException;
import com.brandon.services.feeds.GoogleNewService;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class NewsRestController {
    private final GoogleNewService googleNewService;

    /**
     * spring thread 내에서 관리되는 async task
     *
     * @return
     * @throws NewFeedException
     */
    @GetMapping("news")
    public Callable<List<Message<SyndEntry>>> news() throws NewFeedException {
        try {
            return googleNewService::recentlyTop10;
        } catch (Exception e) {
            throw new NewFeedException(e);
        }
    }

    /**
     * spring thread 밖에서 관리되는 async task
     *
     * @return
     * @throws NewFeedException
     */
    @GetMapping("gnews")
    public DeferredResult<List<Message<SyndEntry>>> gnews() throws NewFeedException {
        try {
            DeferredResult<List<Message<SyndEntry>>> deferredResult = new DeferredResult<>();
            CompletableFuture
                    .supplyAsync(googleNewService::recentlyTop10)
                    .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
            return deferredResult;
        } catch (Exception e) {
            throw new NewFeedException(e);
        }
    }

    /**
     * rest call exception 발생 시, json 오류를 반환한다.
     */
    @ExceptionHandler(NewFeedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Can't access api")
    public void exception() {
    }
}