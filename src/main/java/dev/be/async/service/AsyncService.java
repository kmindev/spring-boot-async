package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public String asyncReturnCall_1() {
        log.info("asyncReturnCall_1");
        StringBuilder result = new StringBuilder();

        List<CompletableFuture<Void>> futures = new ArrayList<>(); // CompletableFuture 리스트 생성

        for (int i = 0; i < 10; i++) {
            // 비동기 메서드 호출
            CompletableFuture<Void> future = emailService.sendMailWithReturn(i)
                    .thenAccept(res -> {
                        synchronized (result) { // 스레드 동기화 => race condition 방지
                            result.append(res); // 결과를 비동기적으로 바로 추가
                        }
                    })
                    .exceptionally(e -> {
                        log.error("예외 발생: {}", e.getMessage());
                        return null;
                    });

            futures.add(future); // CompletableFuture 리스트에 추가
        }

        // 모든 작업들이 끝나길 기다리지 않고 비동기 처리됨

        // 모든 작업 완료 후 반환
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return result.toString(); // 결과 반환
    }


}
