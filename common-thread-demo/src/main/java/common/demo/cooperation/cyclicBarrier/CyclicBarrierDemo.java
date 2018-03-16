package common.demo.cooperation.cyclicBarrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier demo
 *
 * @author zhangjj
 * @create 2018-03-16 17:59
 **/
public class CyclicBarrierDemo {

    private static Logger logger = LoggerFactory.getLogger(CyclicBarrierDemo.class);


    public static void main(String[] args) {
        int perlimits = 20;
        CyclicBarrier barrier = new CyclicBarrier(perlimits, new Runnable() {
            @Override
            public void run() {
                System.out.println();
                logger.info("{} 最后一个到集合点,集合完毕...", Thread.currentThread().getName());
                System.out.println();
            }
        });
        for(int i = 0; i < perlimits; ++i){
           new Thread(new Tourist(barrier)).start();
        }
    }
}
