package common.demo.cooperation.barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集合点
 *
 * @author zhangjj
 * @create 2018-03-15 17:07
 **/
public class MyBarrier {

    private Logger logger = LoggerFactory.getLogger(MyBarrier.class);

    private int count;

    private int backup;

    public MyBarrier(int count) {
        this.count = count;
        this.backup = count;
    }

    public synchronized void await() throws InterruptedException {
        count--;
        if(count == 0){
            logger.info("集合完毕...");
            notifyAll();
        }else{
            wait();
        }
    }

    public synchronized void reset() throws InterruptedException {
        if(count == 0){
            count = backup;
        }else{
            wait();
        }
    }
}
