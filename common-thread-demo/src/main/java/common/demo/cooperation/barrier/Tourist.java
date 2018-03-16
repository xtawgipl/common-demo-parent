package common.demo.cooperation.barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多个游客线程，各自先独立运行，然后使用该协作对象到达集合点进行同步
 *
 * @author zhangjj
 * @create 2018-03-15 17:10
 **/
public class Tourist implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Tourist.class);

    private MyBarrier barrier;

    public Tourist(MyBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
            logger.info("{} 完成，回到集合点等待集合完毕", Thread.currentThread().getName());
            barrier.await();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
