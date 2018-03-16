package common.demo.cooperation.fire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务
 *
 * @author zhangjj
 * @create 2018-03-15 16:29
 **/
public class Task implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Task.class);

    private FireFlag fireFlag;

    public Task(FireFlag fireFlag) {
        this.fireFlag = fireFlag;
    }

    @Override
    public void run() {
        try {
            this.fireFlag.waitForFire();
            Thread.sleep(500L);
            logger.info("开始 : {}", this);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
