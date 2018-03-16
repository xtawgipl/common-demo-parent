package common.demo.cooperation.latch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch demo
 *
 * @author zhangjj
 * @create 2018-03-16 17:32
 **/
public class CountDownLatchDemo {
    private static Logger logger = LoggerFactory.getLogger(CountDownLatchDemo.class);

    public static void main(String[] args) throws InterruptedException {
        int count = 20;
        CountDownLatch latch = new CountDownLatch(count);
        for(int i = 0; i < count; ++i){
            new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    logger.info("{} 执行完成...", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    logger.error("", e);
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        logger.info("主程序开始执行...");
    }
}
