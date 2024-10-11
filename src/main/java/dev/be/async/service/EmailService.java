package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<String> sendMailWithReturn(int i) {
        System.out.println("[sendMailWithReturnThreadPool] :: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(i + " ");
    }

}
