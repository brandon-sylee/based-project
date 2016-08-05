package com.brandon.services.jobs;

import com.google.common.collect.Queues;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-08-04.
 */
public class JobManagerServiceTest {
    final Logger logger = getLogger(getClass());
    final ThreadGroup threadGroup = new ThreadGroup("jobs");
    ExecutorService executorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
    ExecutorService jobs = Executors.newFixedThreadPool(4, r -> new Thread(threadGroup, r));
    Queue<Runnable> tasks = Queues.newConcurrentLinkedQueue();
    private Random random = new Random();

    @Test
    public void threadChecker() throws Exception {
        while (true) {
            logger.info("Current Active Count {}", threadGroup.activeCount());
            executorService.submit(() ->
                    tasks.parallelStream().forEach(runnable -> {
                        Future<?> future = jobs.submit(tasks.poll());
                        try {
                            future.get(7, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                    })
            );
            sleep("sleep", 1);
            if (threadGroup.activeCount() <= 0) break;
        }
    }

    @Before
    public void makeTask() {
        IntStream.range(0, 100).forEach(x -> tasks.add(() -> sleep("gogo", randomInt(1, 10))));
    }

    private void sleep(String txt, int second) {
        try {
            logger.info("{} {}", txt, second);
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}