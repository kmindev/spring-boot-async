package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class EmailService {

    @Async("defaultTaskExecutor")
    public void sendMail() {
        System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    }

    @Async("smallTaskExecutor")
    public void sendMailWithSmallTaskExecutor() {
        System.out.println("[sendMailWithSmallTaskExecutor] :: " + Thread.currentThread().getName());
    }

    @Async("messagingTaskExecutor")
    public void sendMailWithCustomThreadPool() {
        System.out.println("[sendMailWithCustomThreadPool] :: " + Thread.currentThread().getName());
    }

    @Async
    public Future<String> sendMailWithFuture(int i) throws InterruptedException {
        System.out.println("task start : " + i);
        Thread.sleep(1000);
        return new AsyncResult<>("task end : " + i);
    }

    @Async
    public ListenableFuture<String> sendMailWithListenableFuture(int i) throws InterruptedException {
        System.out.println("task start : " + i);
        Thread.sleep(1000);
        return new AsyncResult<>("task end : " + i);
    }

    @Async
    public CompletableFuture<String> sendMailWithCompletableFuture(int i) throws InterruptedException {
        System.out.println("task start : " + i);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture("task end : " + i);
    }

}
