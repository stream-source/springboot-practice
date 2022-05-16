package com.itwx.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AsyncTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void asyncTest01() {
        asyncService.configTest();
    }
}
