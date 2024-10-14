package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncService {

    private final EmailService emailService;
    private final Email2Service email2Service;

    public void asyncCall_1() {
        log.info("asyncCall_1");
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    public void asyncCall_2() {
        log.info("asyncCall_2");
        EmailService emailService1 = new EmailService();
        emailService1.sendMail();
        emailService1.sendMailWithCustomThreadPool();
    }

    public void asyncCall_3() {
        log.info("asyncCall_3");
        sendMail();
    }

    @Async
    private void sendMail() {
        log.info("private sendMail");
    }

    public void asyncCall_4() {
        log.info("asyncCall_4");
        email2Service.sendMail2();
    }

    public void asyncCall_5() {
        log.info("asyncCall_5");
        try {
            for (int i = 0; i < 100; i++) {
                emailService.sendMailWithSmallTaskExecutor();
            }
        } catch (TaskRejectedException e) {
            // 예외 처리
        }
    }

    public void asyncFutureCall() throws InterruptedException {
        List<Future<String>> futures = new ArrayList<>();

        // 비동기 메서드 호출
        for (int i = 0; i < 10; i++) {
            futures.add(emailService.sendMailWithFuture(i));
        }

        // 결과 출력
        for (Future<String> future : futures) {
            try {
                System.out.println(future.get()); // Future는 블로킹하여 결과를 출력
            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
    }

    public void asyncListenableFutureCall() throws InterruptedException {
        List<ListenableFuture<String>> futures = new ArrayList<>();

        // 비동기 메서드 호출
        for (int i = 0; i < 10; i++) {
            futures.add(emailService.sendMailWithListenableFuture(i));
        }

        // 결과 출력 및 예외 처리
        for (ListenableFuture<String> future : futures) {
            // 콜백 등록
            future.addCallback(
                    System.out::println, // 정상 완료시 콜백
                    ex -> System.err.println("Exception: " + ex.getMessage()) // 예외 처리 콜백
            );

            try {
                future.get(); // 작업 완료까지 대기
            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
    }

    public void asyncCompletableFutureCall() throws InterruptedException {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        // 비동기 메서드 호출
        for (int i = 0; i < 10; i++) {
            futures.add(emailService.sendMailWithCompletableFuture(i));
        }

        // CompletableFuture로 결과 출력
        for (CompletableFuture<String> future : futures) {
            future.thenAccept(System.out::println)
                    .exceptionally(error -> {
                        System.err.println("Exception: " + error.getMessage());
                        return null;
                    });
        }

        // 모든 작업 완료까지 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

}
