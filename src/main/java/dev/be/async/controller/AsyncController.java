package dev.be.async.controller;

import dev.be.async.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AsyncController {

    private final AsyncService asyncService;

    @GetMapping("/1")
    public String asyncCall_1() {
        asyncService.asyncCall_1();
        return "success";
    }

    @GetMapping("/2")
    public String asyncCall_2() {
        asyncService.asyncCall_2();
        return "success";
    }

    @GetMapping("/3")
    public String asyncCall_3() {
        asyncService.asyncCall_3();
        return "success";
    }

    @GetMapping("/4")
    public String asyncReturnCall1() throws InterruptedException {
        asyncService.asyncListenableFutureCall();
        return "success";
    }

}
