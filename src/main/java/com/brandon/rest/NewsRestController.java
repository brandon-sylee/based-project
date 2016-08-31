package com.brandon.rest;

import com.brandon.rest.exceptions.NewFeedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@CrossOrigin
public class NewsRestController {
    private final PollableChannel feedPublishingChannel;

    /**
     * spring thread 내에서 관리되는 async task
     *
     * @return
     * @throws NewFeedException
     */
    @GetMapping("news")
    public Callable<Message<?>> news() throws NewFeedException {
        try {
            return feedPublishingChannel::receive;
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
    public DeferredResult<Message<?>> gnews() throws NewFeedException {
        try {
            DeferredResult<Message<?>> deferredResult = new DeferredResult<>();
            CompletableFuture
                    .supplyAsync(feedPublishingChannel::receive)
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
