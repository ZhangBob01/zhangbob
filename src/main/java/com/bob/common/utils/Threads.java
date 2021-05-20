package com.bob.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author: zhang bob
 * @date: 2021-05-17 11:44
 * @description: 线程工具类
 */
@Slf4j
public class Threads {


    /**
     * 停止线程池
     * 1. 调用shtdown，停止接收新任务并尝试完成所有自己存在的任务
     * 2. 如果超时，调用shutdownNow，取消在workQueue中Pending的任务，并终端所有阻塞函数。
     * 3. 如果仍超时，则强制退出.
     * 4. 对在shutdown时线程本身被调用中断做处理
     *
     * @param executorService
     */
    public static void shutdownAndAwaitTermination(ExecutorService executorService) {

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        try {
            if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
                    log.info("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 打印线程异常信息
     */
    public static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            log.error(t.getMessage(), t);
        }
    }
}
