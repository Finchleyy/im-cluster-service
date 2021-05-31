package com.ypw.imserver;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author hongmeng
 * @date 2021/5/31
 */
@Slf4j
public class CompletionServiceTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                20,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024));
        //构建ExecutorCompletionService,与线程池关联
        CompletionService completionService = new ExecutorCompletionService(threadPoolExecutor);
        try {
            //提交任务
            for (int i = 0; i < 100; i++) {
                int temp = i;
                Future submit = completionService.submit(() -> {
                    //线程睡眠模拟业务
                    TimeUnit.SECONDS.sleep(3L);
                    log.info("任务 A执行完毕~{}", System.currentTimeMillis());
                    return "任务 A执行结果" + temp + "";
                });
            }
            //获取返回结果
            for (int i = 0; i < 100; i++) {
                log.info("result:{}", completionService.take().get());
            }
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
