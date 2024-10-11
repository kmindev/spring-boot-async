package dev.be.async;

import dev.be.async.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AsyncApplicationTests {

    @Autowired private AsyncService asyncService;

    @Test
    void asyncCall_1_test() {
        asyncService.asyncCall_1();
    }

    @Test
    void asyncCall_2_test() {
        asyncService.asyncCall_2();
    }

    @Test
    void asyncCall_3_test() {
        asyncService.asyncCall_3();
    }

    @Test
    void asyncCall_4_test() {
        asyncService.asyncCall_4();
    }

    @Test
    void asyncCall_5_test() {
        asyncService.asyncCall_5();
    }

    @Test
    void asyncCall_6_test() {
        String result = asyncService.asyncReturnCall_1();
        System.out.println("result = " + result);
    }

}
