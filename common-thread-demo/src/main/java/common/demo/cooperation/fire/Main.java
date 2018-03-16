package common.demo.cooperation.fire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟多线程同时开始协作模式
 *
 * @author zhangjj
 * @create 2018-03-15 16:32
 **/
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Task.class);

    public static void main(String[] args) throws InterruptedException {
        FireFlag fireFlag = new FireFlag();
        for(int i = 0; i < 20; ++i){
            Task task = new Task(fireFlag);
            new Thread(task).start();
        }
        logger.info("main runing...");
        Thread.sleep(2000L);//模拟主程序执行时间
        fireFlag.fire();
    }
}
