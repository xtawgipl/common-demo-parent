package common.demo.cooperation.barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主程序
 *
 * @author zhangjj
 * @create 2018-03-15 17:12
 **/
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws InterruptedException {
        logger.info("开始任务");
        int count = 5;
        MyBarrier barrier = new MyBarrier(count);
        for(int i = 0; i < count; ++i){
            Tourist tourist = new Tourist(barrier);
            new Thread(tourist).start();
        }
        Thread.sleep(3000L);//模拟集合后主程序工作
        barrier.reset();
        for(int i = 0; i < count; ++i){
            Tourist tourist = new Tourist(barrier);
            new Thread(tourist).start();
        }
    }
}
