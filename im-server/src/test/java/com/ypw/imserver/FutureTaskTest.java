package com.ypw.imserver;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author hongmeng
 * @date 2021/5/28
 */
@Slf4j
public class FutureTaskTest {

    @Test
    void futureTest() {

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                20,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024),
                new DefaultThreadFactory("im-cluster-io-executor"),
                (r, t) -> {
                    log.error("[blocking-io-executor] 任务提交失败, 线程池信息: {}", t);
                    throw new RuntimeException("IO线程池溢出, 任务提交失败");
                });
        try {
            for (int i = 0; i < 100; i++) {
                //提交线程池任务
                Future<String> submit = threadPoolExecutor.submit(() -> {
                    //线程睡眠模拟业务
                    TimeUnit.SECONDS.sleep(3L);
                    log.info("任务 A执行完毕~{}", System.currentTimeMillis());
                    return "任务 A执行结果";
                });
                //TimeUnit.SECONDS.sleep(5L);
                //if (submit.isDone()) {
                //这里的 get 方法会阻塞后面的线程 (默认的实现类是FutureTask)
                String futureResult = submit.get();
                log.info("future result is:{}", futureResult);
            }
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
