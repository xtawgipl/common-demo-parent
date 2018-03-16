package common.demo.cooperation.cyclicBarrier;

import common.demo.cooperation.barrier.MyBarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhangjj
 * @create 2018-03-16 17:59
 **/
public class Tourist implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Tourist.class);

    private CyclicBarrier barrier;

    public Tourist(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
            logger.info("{} 第一次命令前各干各的事", Thread.currentThread().getName());
            barrier.await();

            Thread.sleep(2000L);
            logger.info("{} 第二次命令前各干各的事", Thread.currentThread().getName());
            barrier.await();

            logger.info("{} 解散回家", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            logger.error("", e);
        } catch (BrokenBarrierException e) {
            logger.error("", e);
        }
    }

}
