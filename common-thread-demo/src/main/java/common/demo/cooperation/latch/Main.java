package common.demo.cooperation.latch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟等待所有子线程执行完再执行主线程
 *
 * @author zhangjj
 * @create 2018-03-15 16:44
 **/
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        int count = 20;
        MyLatch latch = new MyLatch(count);
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
