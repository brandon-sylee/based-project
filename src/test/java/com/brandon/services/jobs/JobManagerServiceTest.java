package com.brandon.services.jobs;

import com.google.common.collect.Queues;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-08-04.
 */
public class JobManagerServiceTest {
    final Logger logger = getLogger(getClass());

    ExecutorService executorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
    final ThreadGroup threadGroup = new ThreadGroup("jobs");
    ExecutorService jobs = Executors.newFixedThreadPool(4, r -> new Thread(threadGroup, r));
    Queue<Runnable> tasks = Queues.newConcurrentLinkedQueue();

    @Test
    public void 쓰레드는이렇게() throws Exception {
        IntStream.range(0, 10).forEach(x -> {
            executorService.submit(() -> {
                tasks.stream().peek(runnable -> jobs.submit(runnable)).forEach(runnable -> tasks.poll());
            });

            try {
                TimeUnit.SECONDS.sleep(1);
                IntStream.range(1, 10).forEach(value -> {
                    tasks.add(() -> {
                        sleep("gogo", 5);
                    });
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (threadGroup.activeCount() <=0) {
            sleep("sleep", 1);
            logger.info("Current Active Count {}", threadGroup.activeCount());
        }
    }

    private void sleep(String txt, int second) {
        try {
            logger.info("{} {}", txt, second);
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}