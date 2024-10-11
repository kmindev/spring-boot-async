package dev.be.async.service;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class Email2Service {

    @Async("defaultTaskExecutor")
    public void sendMail2() {
        System.out.println("[sendMail2] :: " + Thread.currentThread().getName());
    }

}
